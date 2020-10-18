package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.items.gems.Gem;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.PutCrystalsInInventoryMessage;
import machinamelia.ethergems.common.network.server.PutGemsInInventoryMessage;

import java.util.Random;

import static machinamelia.ethergems.common.container.EtherFurnaceContainer.readItemStacksFromTag;
import static machinamelia.ethergems.common.container.EtherFurnaceContainer.writeItemStacksToTag;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class PlayerUpdateEvents {
    @SubscribeEvent
    public static void playerUpdateEvent(PlayerEvent event) {
        if (event.getPlayer() != null) {
            if (event.getPlayer().inventory != null && event.getPlayer().inventory.mainInventory != null) {
                PlayerInventory inventory = event.getPlayer().inventory;
                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    if (inventory.getStackInSlot(i).getItem() instanceof Cylinder) {
                        if (event instanceof InputUpdateEvent || event instanceof PlayerEvent.ItemPickupEvent) {
                            ItemStack[] cylinders = new ItemStack[1];
                            cylinders[0] = inventory.getStackInSlot(i);
                            if (event.getPlayer().world.isRemote) {
                                PutCrystalsInInventoryMessage putCrystalsInInventoryMessage = new PutCrystalsInInventoryMessage(cylinders);
                                NetworkHandler.simpleChannel.sendToServer(putCrystalsInInventoryMessage);
                            } else {
                                if (cylinders != null) {
                                    CompoundNBT compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("crystal_inventory");
                                    ItemStack[] items = new ItemStack[42];
                                    if (compoundNBT != null) {
                                        ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                                        readItemStacksFromTag(items, listNBT);
                                    }
                                    for (ItemStack crystal : cylinders) {
                                        boolean itemPlaced = false;
                                        for (int j = 0; j < 42; j++) {
                                            if (!itemPlaced && items[j] != null && items[j].getItem().equals(Items.AIR)) {
                                                items[j] = crystal;
                                                itemPlaced = true;
                                            } else if (!itemPlaced && items[j] == null) {
                                                items[j] = crystal;
                                                itemPlaced = true;
                                            }
                                        }
                                    }
                                    compoundNBT = new CompoundNBT();
                                    compoundNBT.put("Items", writeItemStacksToTag(items, 42));
                                    compoundNBT.putByte("size", (byte) 42);
                                    event.getPlayer().getPersistentData().put("crystal_inventory", compoundNBT);
                                }
                            }
                        }
                        inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    } else if (inventory.getStackInSlot(i).getItem() instanceof Crystal) {
                        if (event instanceof InputUpdateEvent || event instanceof PlayerEvent.ItemPickupEvent) {
                            ItemStack itemStack = inventory.getStackInSlot(i);
                            LazyOptional<ICrystal> crystalCapability = itemStack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                            ICrystal crystalImpl = crystalCapability.orElse(new CrystalInstance());
                            crystalImpl.init();
                            Random randy = new Random();
                            int randomInt = randy.nextInt(100);
                            switch(PlayerWorldEvents.getCrystalLevel()) {
                                case 0:
                                    crystalImpl.setLevel(1);
                                    break;
                                case 1:
                                    if (randomInt < 50) {
                                        crystalImpl.setLevel(2);
                                    } else {
                                        crystalImpl.setLevel(1);
                                    }
                                    break;
                                case 2:
                                    if (randomInt < 33) {
                                        crystalImpl.setLevel(3);
                                    } else if (randomInt < 67) {
                                        crystalImpl.setLevel(2);
                                    } else {
                                        crystalImpl.setLevel(1);
                                    }
                                    break;
                                case 3:
                                    if (randomInt < 33) {
                                        crystalImpl.setLevel(4);
                                    } else if (randomInt < 67) {
                                        crystalImpl.setLevel(3);
                                    } else {
                                        crystalImpl.setLevel(2);
                                    }
                                    break;
                                case 4:
                                    if (randomInt < 25) {
                                        crystalImpl.setLevel(5);
                                    } else if (randomInt < 50) {
                                        crystalImpl.setLevel(4);
                                    } else if (randomInt < 75) {
                                        crystalImpl.setLevel(3);
                                    }  else {
                                        crystalImpl.setLevel(2);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            ItemStack[] crystals = new ItemStack[1];
                            crystals[0] = inventory.getStackInSlot(i);
                            if (event.getPlayer().world.isRemote) {
                                PutCrystalsInInventoryMessage putCrystalsInInventoryMessage = new PutCrystalsInInventoryMessage(crystals);
                                NetworkHandler.simpleChannel.sendToServer(putCrystalsInInventoryMessage);
                            } else {
                                if (crystals != null) {
                                    CompoundNBT compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("crystal_inventory");
                                    ItemStack[] items = new ItemStack[42];
                                    if (compoundNBT != null) {
                                        ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                                        readItemStacksFromTag(items, listNBT);
                                    }
                                    for (ItemStack crystal : crystals) {
                                        boolean itemPlaced = false;
                                        for (int j = 0; j < 42; j++) {
                                            if (!itemPlaced && items[j] != null && items[j].getItem().equals(Items.AIR)) {
                                                items[j] = crystal;
                                                itemPlaced = true;
                                            } else if (!itemPlaced && items[j] == null) {
                                                items[j] = crystal;
                                                itemPlaced = true;
                                            }
                                        }
                                    }
                                    compoundNBT = new CompoundNBT();
                                    compoundNBT.put("Items", writeItemStacksToTag(items, 42));
                                    compoundNBT.putByte("size", (byte) 42);
                                    event.getPlayer().getPersistentData().put("crystal_inventory", compoundNBT);
                                }
                            }

                        }
                        inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    } else if (inventory.getStackInSlot(i).getItem() instanceof Gem) {
                        if (event instanceof InputUpdateEvent || event instanceof PlayerEvent.ItemPickupEvent) {
                            ItemStack[] gems = new ItemStack[1];
                            gems[0] = inventory.getStackInSlot(i);
                            if (event.getPlayer().world.isRemote) {
                                PutGemsInInventoryMessage putGemsInInventoryMessage = new PutGemsInInventoryMessage(gems);
                                NetworkHandler.simpleChannel.sendToServer(putGemsInInventoryMessage);
                            } else {
                                if (gems != null) {
                                    CompoundNBT compoundNBT = (CompoundNBT) event.getPlayer().getPersistentData().get("gem_inventory");
                                    ItemStack[] items = new ItemStack[44];
                                    if (compoundNBT != null) {
                                        ListNBT listNBT = (ListNBT) compoundNBT.get("Items");
                                        readItemStacksFromTag(items, listNBT);
                                    }
                                    for (ItemStack gem : gems) {
                                        boolean itemPlaced = false;
                                        for (int j = 0; j < 44; j++) {
                                            if (!itemPlaced && items[j] != null && items[j].getItem().equals(Items.AIR)) {
                                                items[j] = gem;
                                                itemPlaced = true;
                                            } else if (!itemPlaced && items[j] == null) {
                                                items[j] = gem;
                                                itemPlaced = true;
                                            }
                                        }
                                    }
                                    compoundNBT = new CompoundNBT();
                                    compoundNBT.put("Items", writeItemStacksToTag(items, 44));
                                    compoundNBT.putByte("size", (byte) 44);
                                    event.getPlayer().getPersistentData().put("gem_inventory", compoundNBT);
                                }
                            }
                        }
                        inventory.setInventorySlotContents(i, ItemStack.EMPTY);
                    }

                }
            }
        }
    }

}
