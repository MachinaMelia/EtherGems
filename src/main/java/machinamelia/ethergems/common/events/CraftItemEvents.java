package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerListener;
import net.minecraft.inventory.container.SmithingTableContainer;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.*;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorInstance;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponInstance;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.items.armor.material.SlottedArmor;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CraftItemEvents {
    @SubscribeEvent
    public static void getCraftedEvent(PlayerEvent.ItemCraftedEvent event) {
        ItemStack itemStack = event.getCrafting();
        Item item = itemStack.getItem();
        if (item instanceof SlottedArmor) {
            LazyOptional<ISlottedArmor> armorCapability = itemStack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
            if (!armor.getHasInited()) {
                armor.init();
                event.getInventory().setChanged();
            }
        }
        if (item instanceof SlottedSword || item instanceof SlottedAxe) {
            LazyOptional<ISlottedWeapon> weaponCapability = itemStack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
            if (!weapon.getHasInited()) {
                weapon.init();
                event.getInventory().setChanged();
            }
        }
    }
    @SubscribeEvent
    public static void craftingContainerEvent(PlayerContainerEvent event) {
        if (event.getContainer() instanceof WorkbenchContainer) {
            WorkbenchContainer workbenchContainer = (WorkbenchContainer) event.getContainer();
            workbenchContainer.addSlotListener(new IContainerListener() {
                @Override
                public void refreshContainer(Container containerToSend, NonNullList<ItemStack> itemsList) {
                    for (int i = 0; i < itemsList.size(); i++) {
                        ItemStack itemStack = itemsList.get(i);
                        if (itemStack.getItem() instanceof SlottedArmor) {
                            LazyOptional<ISlottedArmor> armorCapability = itemStack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
                            if (!armor.getHasInited()) {
                                armor.init();
                                if (armor.getSlots() < 1) {
                                    itemStack = resetItem(itemStack, armor);
                                }
                            }
                        }
                        if (itemStack.getItem() instanceof SlottedSword || itemStack.getItem() instanceof SlottedAxe) {
                            LazyOptional<ISlottedWeapon> weaponCapability = itemStack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
                            if (!weapon.getHasInited()) {
                                weapon.init();
                                if (weapon.getSlots() < 1) {
                                    itemStack = resetItem(itemStack, weapon);
                                }
                            }
                        }
                        containerToSend.setItem(i, itemsList.get(i));
                    }
                }

                @Override
                public void slotChanged(Container containerToSend, int slotInd, ItemStack stack) {
                    if (containerToSend.getSlot(slotInd) != null) {
                        if (stack.getItem() instanceof SlottedArmor) {
                            LazyOptional<ISlottedArmor> armorCapability = stack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
                            if (!armor.getHasInited()) {
                                armor.init();
                                if (armor.getSlots() < 1) {
                                    stack = resetItem(stack, armor);
                                }
                            }
                        }
                        if (stack.getItem() instanceof SlottedSword || stack.getItem() instanceof SlottedAxe) {
                            LazyOptional<ISlottedWeapon> weaponCapability = stack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
                            if (!weapon.getHasInited()) {
                                weapon.init();
                                if (weapon.getSlots() < 1) {
                                    stack = resetItem(stack, weapon);
                                }
                            }
                        }
                        containerToSend.setItem(slotInd, stack);
                    }
                }

                @Override
                public void setContainerData(Container containerIn, int varToUpdate, int newValue) {

                }
            });
        } else if (event.getContainer() instanceof SmithingTableContainer) {
            SmithingTableContainer smithingTableContainer = (SmithingTableContainer) event.getContainer();
            smithingTableContainer.addSlotListener(new IContainerListener() {
                @Override
                public void refreshContainer(Container containerToSend, NonNullList<ItemStack> itemsList) {
                    for (int i = 0; i < itemsList.size(); i++) {
                        ItemStack itemStack = itemsList.get(i);
                        if (itemStack.getItem() instanceof SlottedArmor) {
                            LazyOptional<ISlottedArmor> armorCapability = itemStack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
                            if (!armor.getHasInited()) {
                                armor.init();
                                if (armor.getSlots() < 1) {
                                    itemStack = resetItem(itemStack, armor);
                                }
                            }
                        }
                        if (itemStack.getItem() instanceof SlottedSword || itemStack.getItem() instanceof SlottedAxe) {
                            LazyOptional<ISlottedWeapon> weaponCapability = itemStack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
                            if (!weapon.getHasInited()) {
                                weapon.init();
                                if (weapon.getSlots() < 1) {
                                    itemStack = resetItem(itemStack, weapon);
                                }
                            }
                        }
                        containerToSend.setItem(i, itemsList.get(i));
                    }
                }

                @Override
                public void slotChanged(Container containerToSend, int slotInd, ItemStack stack) {
                    if (containerToSend.getSlot(slotInd) != null) {
                        if (stack.getItem() instanceof SlottedArmor) {
                            LazyOptional<ISlottedArmor> armorCapability = stack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                            ISlottedArmor armor = armorCapability.orElse(new SlottedArmorInstance());
                            if (!armor.getHasInited()) {
                                armor.init();
                                if (armor.getSlots() < 1) {
                                    stack = resetItem(stack, armor);
                                }
                            }
                        }
                        if (stack.getItem() instanceof SlottedSword || stack.getItem() instanceof SlottedAxe) {
                            LazyOptional<ISlottedWeapon> weaponCapability = stack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                            ISlottedWeapon weapon = weaponCapability.orElse(new SlottedWeaponInstance());
                            if (!weapon.getHasInited()) {
                                weapon.init();
                                if (weapon.getSlots() < 1) {
                                    stack = resetItem(stack, weapon);
                                }
                            }
                        }
                        containerToSend.setItem(slotInd, stack);
                    }
                }

                @Override
                public void setContainerData(Container containerIn, int varToUpdate, int newValue) {

                }
            });
        }
    }

    public static ItemStack resetItem(ItemStack stack, ISlottedWeapon weapon) {
        if (stack.getItem() instanceof SlottedSword) {
            SwordItem swordItem = (SwordItem) stack.getItem();
            if (swordItem.getTier().equals(ItemTier.WOOD)) {
                stack = new ItemStack(Items.WOODEN_SWORD);
            } else if (swordItem.getTier().equals(ItemTier.STONE)) {
                stack = new ItemStack(Items.STONE_SWORD);
            } else if (swordItem.getTier().equals(ItemTier.IRON)) {
                stack = new ItemStack(Items.IRON_SWORD);
            } else if (swordItem.getTier().equals(ItemTier.GOLD)) {
                stack = new ItemStack(Items.GOLDEN_SWORD);
            } else if (swordItem.getTier().equals(ItemTier.DIAMOND)) {
                stack = new ItemStack(Items.DIAMOND_SWORD);
            } else if (swordItem.getTier().equals(ItemTier.NETHERITE)) {
                stack = new ItemStack(Items.NETHERITE_SWORD);
            }
        }
        if (stack.getItem() instanceof SlottedAxe) {
            AxeItem axeItem = (AxeItem) stack.getItem();
            if (axeItem.getTier().equals(ItemTier.WOOD)) {
                stack = new ItemStack(Items.WOODEN_AXE);
            } else if (axeItem.getTier().equals(ItemTier.STONE)) {
                stack = new ItemStack(Items.STONE_AXE);
            } else if (axeItem.getTier().equals(ItemTier.IRON)) {
                stack = new ItemStack(Items.IRON_AXE);
            } else if (axeItem.getTier().equals(ItemTier.GOLD)) {
                stack = new ItemStack(Items.GOLDEN_AXE);
            } else if (axeItem.getTier().equals(ItemTier.DIAMOND)) {
                stack = new ItemStack(Items.DIAMOND_AXE);
            } else if (axeItem.getTier().equals(ItemTier.NETHERITE)) {
                stack = new ItemStack(Items.NETHERITE_AXE);
            }
        }
        return stack;
    }

    public static ItemStack resetItem(ItemStack stack, ISlottedArmor armor) {
        if (stack.getItem() instanceof SlottedArmor) {
            ArmorItem armorItem = (ArmorItem) stack.getItem();
            if (armorItem.getMaterial().equals(ArmorMaterial.LEATHER)) {
                if (armorItem.getSlot().equals(EquipmentSlotType.HEAD)) {
                    stack = new ItemStack(Items.LEATHER_HELMET);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.CHEST)) {
                    stack = new ItemStack(Items.LEATHER_CHESTPLATE);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.LEGS)) {
                    stack = new ItemStack(Items.LEATHER_LEGGINGS);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.FEET)) {
                    stack = new ItemStack(Items.LEATHER_BOOTS);
                }
            } else if (armorItem.getMaterial().equals(ArmorMaterial.IRON)) {
                if (armorItem.getSlot().equals(EquipmentSlotType.HEAD)) {
                    stack = new ItemStack(Items.IRON_HELMET);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.CHEST)) {
                    stack = new ItemStack(Items.IRON_CHESTPLATE);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.LEGS)) {
                    stack = new ItemStack(Items.IRON_LEGGINGS);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.FEET)) {
                    stack = new ItemStack(Items.IRON_BOOTS);
                }
            } else if (armorItem.getMaterial().equals(ArmorMaterial.GOLD)) {
                if (armorItem.getSlot().equals(EquipmentSlotType.HEAD)) {
                    stack = new ItemStack(Items.GOLDEN_HELMET);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.CHEST)) {
                    stack = new ItemStack(Items.GOLDEN_CHESTPLATE);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.LEGS)) {
                    stack = new ItemStack(Items.GOLDEN_LEGGINGS);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.FEET)) {
                    stack = new ItemStack(Items.GOLDEN_BOOTS);
                }
            } else if (armorItem.getMaterial().equals(ArmorMaterial.DIAMOND)) {
                if (armorItem.getSlot().equals(EquipmentSlotType.HEAD)) {
                    stack = new ItemStack(Items.DIAMOND_HELMET);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.CHEST)) {
                    stack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.LEGS)) {
                    stack = new ItemStack(Items.DIAMOND_LEGGINGS);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.FEET)) {
                    stack = new ItemStack(Items.DIAMOND_BOOTS);
                }
            } else if (armorItem.getMaterial().equals(ArmorMaterial.NETHERITE)) {
                if (armorItem.getSlot().equals(EquipmentSlotType.HEAD)) {
                    stack = new ItemStack(Items.NETHERITE_HELMET);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.CHEST)) {
                    stack = new ItemStack(Items.NETHERITE_CHESTPLATE);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.LEGS)) {
                    stack = new ItemStack(Items.NETHERITE_LEGGINGS);
                } else if (armorItem.getSlot().equals(EquipmentSlotType.FEET)) {
                    stack = new ItemStack(Items.NETHERITE_BOOTS);
                }
            }
        }
        return stack;
    }
}
