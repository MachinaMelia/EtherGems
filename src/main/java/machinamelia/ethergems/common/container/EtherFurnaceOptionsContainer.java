package machinamelia.ethergems.common.container;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.ItemStackHandler;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.OpenCraftingContainerMessage;
import machinamelia.ethergems.common.network.server.PutCrystalsInInventoryMessage;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;

import java.util.Objects;

public class EtherFurnaceOptionsContainer extends EtherFurnaceContainer {

    public final EtherFurnaceTileEntity tileEntity;
    public final ItemStackHandler items;
    private final IWorldPosCallable canInteractWithCallable;

    public EtherFurnaceOptionsContainer(final int windowId, final PlayerInventory playerInventory, final EtherFurnaceTileEntity tileEntity) {
        super(ContainerInit.ETHER_FURNACE_OPTIONS_CONTAINER.get(), windowId, playerInventory, tileEntity);
        this.tileEntity = tileEntity;
        this.items = new ItemStackHandler(8);
        this.size = 8;
        this.canInteractWithCallable = IWorldPosCallable.create(tileEntity.getLevel(), tileEntity.getBlockPos());
        this.initSlots();
        this.initInventory();
    }
    public EtherFurnaceTileEntity getTileEntity() {
        return tileEntity;
    }
    private static EtherFurnaceTileEntity getTileEntity(final PlayerInventory playerInventory, final PacketBuffer data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final TileEntity tileAtPos = playerInventory.player.level.getBlockEntity(data.readBlockPos());
        if (tileAtPos instanceof EtherFurnaceTileEntity) {
            return (EtherFurnaceTileEntity) tileAtPos;
        }
        throw new IllegalStateException("Tile entity is not correct! " + tileAtPos);
    }

    @Override
    public ItemStack[] getCurrentSlotStacks() {
        for (int i = 0; i < 8; i++) {
            currentSlotStacks[i] = this.getSlot(i).getItem();
        }
        return currentSlotStacks;
    }

    public EtherFurnaceOptionsContainer(final int windowId, final PlayerInventory playerInventory, final PacketBuffer data) {
        this(windowId, playerInventory, getTileEntity(playerInventory, data));
        this.initSlots();
        this.initInventory();
    }

    public void initInventory() {
        CompoundNBT compoundNBT = (CompoundNBT) this.player.getPersistentData().get("crystal_inventory");
        ItemStack[] items = new ItemStack[44];
        if (compoundNBT != null) {
                ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                if (listNBT != null) {
                    readItemStacksFromTag(items, listNBT);

                    for (int i = 36; i < 44; i++) {
                        if (items[i] != null) {
                            this.setItem(i - 36, items[i]);
                        }
                    }
                }
        }
    }

    public void initSlots() {
        // Gem Inventory
        int startX = 204 + 8;
        int startY = 18;
        int slotSizePlus2 = 18;
        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 4; ++column) {
                this.addSlot(new Slot(tileEntity, (row * 4) + column, startX + (column * slotSizePlus2), startY + (row * slotSizePlus2)));
            }
        }
    }

    public void saveInformation(int shooterIndex, int engineerIndex, int affinityIndex) {
        ItemStack[] items = new ItemStack[8];
        for (int i = 0; i < items.length; i++) {
            items[i] = this.getSlot(i).getItem();
        }
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("Shooter", shooterIndex);
        compoundNBT.putInt("Engineer", engineerIndex);
        compoundNBT.putInt("Affinity", affinityIndex);
        compoundNBT.put("Selected", writeItemStacksToTag(items, 8));
        compoundNBT.putByte("selected_size", (byte) 8);
        this.playerInventory.player.getPersistentData().put("ether_furnace_inventory", compoundNBT);
    }

    @Override
    public void openGui() {
        OpenCraftingContainerMessage openCraftingContainerMessage = new OpenCraftingContainerMessage();
        NetworkHandler.simpleChannel.sendToServer(openCraftingContainerMessage);
    }

    @Override
    public ItemStack clicked(int slotId, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        return ItemStack.EMPTY;
    }
    @Override
    public void setItem(int slotID, ItemStack stack) {
        this.getSlot(slotID).set(stack);
    }

    @Override
    public void removed(PlayerEntity playerIn) {
        if (playerIn.level.isClientSide) {
            if (!(playerIn.containerMenu instanceof EtherFurnaceContainer)) {
                ItemStack[] currentSlotStacks = this.getCurrentSlotStacks();
                PutCrystalsInInventoryMessage putCrystalsInInventoryMessage = new PutCrystalsInInventoryMessage(currentSlotStacks);
                NetworkHandler.simpleChannel.sendToServer(putCrystalsInInventoryMessage);
            }
        }
    }
}
