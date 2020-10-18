package machinamelia.ethergems.common.container;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NumberNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenGemInventoryMessage;
import javax.annotation.Nullable;

public class CrystalInventoryContainer extends Container {

    private int size;
    private ItemStackHandler items = new ItemStackHandler(36);
    private ItemStackHandler trash = new ItemStackHandler(1);
    private ServerPlayerEntity player;

    public CrystalInventoryContainer(@Nullable ContainerType<?> type, final int windowId, PlayerInventory playerInventory) {
        super(type, windowId);
        this.size = 36;
        this.initSlots();
    }
    public CrystalInventoryContainer(@Nullable ContainerType<?> type, final int windowId, ServerPlayerEntity player) {
        this(type, windowId, player.inventory);
        this.player = player;
        this.initInventory(player);
    }
    public CrystalInventoryContainer(int windowId, PlayerInventory inv, PacketBuffer packetBuffer) {
        this(ContainerInit.CRYSTAL_INVENTORY_CONTAINER.get(), windowId, inv);
    }

    public void initSlots() {
        // Crystal Inventory
        int startX = 16 + 115;
        int startY = 18 + 35;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new SlotItemHandler(items, (row * 9) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }

        // Trash
        this.addSlot(new SlotItemHandler(trash, 0, 275, 129));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        return ItemStack.EMPTY;
    }


    protected void initInventory(ServerPlayerEntity player) {
        CompoundNBT compoundNBT = (CompoundNBT) player.getPersistentData().get("crystal_inventory");
        ItemStack[] items = new ItemStack[size];
        if (compoundNBT != null) {
            ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
            readItemStacksFromTag(items, listNBT);
            for (int i = 0; i < 36; i++) {
                this.putStackInSlot(i, items[i]);
            }
        }
    }

    public static ListNBT writeItemStacksToTag(ItemStack[] items, int maxQuantity) {
        ListNBT tagList = new ListNBT();
        for (int i = 0; i < 36; i++) {
            CompoundNBT tag = new CompoundNBT();
            tag.putShort("Slot", (short) i);
            if (items[i] != null) {
                items[i].write(tag);
                if (maxQuantity > Short.MAX_VALUE) {
                    tag.putInt("Quantity", items[i].getCount());
                } else if (maxQuantity > Byte.MAX_VALUE) {
                    tag.putShort("Quantity", (short) items[i].getCount());
                }
            }
            tagList.add(tag);
        }
        return tagList;
    }

    public static void readItemStacksFromTag(ItemStack[] items, ListNBT tagList) {
        for (int i = 0; i < 36; i++) {
            CompoundNBT tag = tagList.getCompound(i);
            int b = tag.getShort("Slot");
            items[b] = ItemStack.read(tag);
            INBT quant = tag.get("Quantity");
            if (quant instanceof NumberNBT) {
                items[b].setCount(((NumberNBT) quant).getInt());
            }
        }
    }

    @Override
    public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        ItemStack stack = super.slotClick(slotId, dragType, clickTypeIn, player);
        if (slotId == 36) {
            this.putStackInSlot(slotId, ItemStack.EMPTY);
            ItemStack[] itemStacks = new ItemStack[36];
            for (int i = 0; i < 36; i++) {
                itemStacks[i] = this.items.getStackInSlot(i);
            }
            CompoundNBT compoundNBT = new CompoundNBT();
            compoundNBT.put("Items", writeItemStacksToTag(itemStacks, 36));
            compoundNBT.putByte("size", (byte) 36);
            player.getPersistentData().put("crystal_inventory", compoundNBT);
        }
        return stack;
    }

    public void openGui() {
        OpenGemInventoryMessage openGemInventoryMessage = new OpenGemInventoryMessage();
        NetworkHandler.simpleChannel.sendToServer(openGemInventoryMessage);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
