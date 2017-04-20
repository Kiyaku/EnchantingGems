package com.seriouscreeper.enchantinggems.tileentity;


import com.seriouscreeper.enchantinggems.helper.SocketsNBTHelper;
import com.seriouscreeper.enchantinggems.items.ItemGem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IntegerCache;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import scala.xml.dtd.EMPTY;

import javax.annotation.Nullable;
import java.util.HashMap;

public class TileEntityGemInfuser extends TileEntity {
    public boolean dirty = false;
    @SideOnly(Side.CLIENT)
    public float hoverValue = 0;
    @SideOnly(Side.CLIENT)
    public float rotation = 0;


    public ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityGemInfuser.this.markDirty();
        }
    };


    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }


    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
    }


    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
    }


    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        inventory.deserializeNBT(compound.getCompoundTag("inventory"));
    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("inventory", inventory.serializeNBT());
        return compound;
    }


    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }

        return super.hasCapability(capability, facing);
    }


    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
            return (T)this.inventory;
        }

        return super.getCapability(capability, facing);
    }


    public void BreakBlock(World world, BlockPos pos) {
        this.invalidate();

        if(inventory != null && !world.isRemote) {
            for(int i = 0; i < inventory.getSlots(); i++) {
                if(inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                    world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, inventory.getStackInSlot(i)));
                }
            }
        }

        world.setTileEntity(pos, null);
    }


    public boolean Activate(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(player.isSneaking()) {
            int toolSlot = -1;
            int gemSlot = -1;
            int ingSlot = -1;

            for(int i = 0; i < inventory.getSlots(); i++) {
                ItemStack tempStack = inventory.getStackInSlot(i);
                if((tempStack != ItemStack.EMPTY)) {
                    if(ItemGem.IsValidItem(tempStack.getItem())) {
                        toolSlot = i;
                    } else if(tempStack.getItem() instanceof ItemGem) {
                        gemSlot = i;
                    } else {
                        ingSlot = i;
                    }
                }
            }

            // We have everything, combine it!
            if(toolSlot >= 0 && gemSlot >= 0 && ingSlot >= 0) {
                if(player.experienceLevel >= 15) {
                    ItemStack toolStack = inventory.getStackInSlot(toolSlot);
                    ItemStack gemStack = inventory.getStackInSlot(gemSlot);
                    ItemStack ingStack = inventory.getStackInSlot(ingSlot);

                    if (ItemGem.CanLevelUp(gemStack) && SocketsNBTHelper.SocketGem(inventory.getStackInSlot(toolSlot), inventory.getStackInSlot(gemSlot))) {
                        Enchantment enchantment = ItemGem.GetEnchantment(gemStack);

                        if(enchantment.canApply(toolStack)) {
                            toolStack.addEnchantment(ItemGem.GetEnchantment(gemStack), ItemGem.GetCurrentLevel(gemStack));
                            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, toolStack));
                            inventory.setStackInSlot(toolSlot, ItemStack.EMPTY);
                            inventory.setStackInSlot(gemSlot, ItemStack.EMPTY);
                            inventory.setStackInSlot(ingSlot, ItemStack.EMPTY);
                            player.removeExperienceLevel(15);
                        } else {
                            player.sendStatusMessage(new TextComponentTranslation("Can't apply enchantment to tool"), true);
                            return false;
                        }
                    }
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("Not enough experience"), true);
                    return false;
                }
            }

            return true;
        }

        if(heldItem == ItemStack.EMPTY && hand == EnumHand.MAIN_HAND) {
            // return items
            for(int i = inventory.getSlots() - 1; i >= 0; i--) {
                if(inventory.getStackInSlot(i) != ItemStack.EMPTY && !world.isRemote) {
                    world.spawnEntity(new EntityItem(world, player.posX, player.posY, player.posZ, inventory.getStackInSlot(i)));
                    inventory.setStackInSlot(i, ItemStack.EMPTY);
                    markDirty();
                    return true;
                }
            }
        } else if(heldItem != ItemStack.EMPTY && hand == EnumHand.MAIN_HAND) {
            for(int i = 0; i < inventory.getSlots(); i++) {
                if(inventory.getStackInSlot(i) == ItemStack.EMPTY) {
                    this.inventory.insertItem(i, heldItem, false);
                    player.setHeldItem(hand, ItemStack.EMPTY);
                    markDirty();
                    return true;
                }
            }
        }

        return false;
    }


    public int GetItemCount() {
        int count = 0;

        for(int i = 0; i < inventory.getSlots(); i++) {
            if(inventory.getStackInSlot(i) != ItemStack.EMPTY) {
                count++;
            }
        }

        return count;
    }


    @Override
    public void markDirty() {
        IBlockState state = world.getBlockState(getPos());
        world.notifyBlockUpdate(getPos(), state, state, 3);
        super.markDirty();
    }
}
