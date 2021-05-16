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

import net.minecraft.item.*;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.capabilities.weapons.ISlottedWeapon;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.items.EtherGemsItemTier;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.*;
import machinamelia.ethergems.common.items.gems.Gem;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class TooltipEvents {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void getTooltipEvent(ItemTooltipEvent event) {
        if (event.getPlayer() != null && event.getPlayer().level.isClientSide) {
            Item item = event.getItemStack().getItem();
            ItemStack itemStack = event.getItemStack();
            if (item instanceof Crystal) {
                LazyOptional<ICrystal> crystalCapability = itemStack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                String attributeString = crystal.getAttributesTooltip();
                if (!attributeString.equals("")) {
                    event.getToolTip().add(1, new StringTextComponent(attributeString));
                }
            }
            if (item instanceof Gem) {
                String attributeString = CreativeGemEvents.makeCreativeGems(itemStack);
                String[] tooltip = attributeString.split("/");
                event.getToolTip().add(1, new StringTextComponent(tooltip[0]));
                event.getToolTip().add(2, new StringTextComponent(tooltip[1]));
            }
            if (item instanceof Cylinder) {
                LazyOptional<ICylinder> cylinderCapability = itemStack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                if (cylinder.getAttribute().equals("")) {
                    if (item instanceof FireCylinder) {
                        cylinder.creativeInit("Fire");
                    } else if (item instanceof WaterCylinder) {
                        cylinder.creativeInit("Water");
                    } else if (item instanceof ElectricCylinder) {
                        cylinder.creativeInit("Electric");
                    } else if (item instanceof IceCylinder) {
                        cylinder.creativeInit("Ice");
                    } else if (item instanceof WindCylinder) {
                        cylinder.creativeInit("Wind");
                    } else if (item instanceof EarthCylinder) {
                        cylinder.creativeInit("Earth");
                    }
                }
                String attributeString = cylinder.getAttributeTooltip();
                event.getToolTip().add(1, new StringTextComponent(attributeString));
            }
            if (item instanceof ArmorItem) {
                LazyOptional<ISlottedArmor> armorCapability = itemStack.getCapability(SlottedArmorProvider.ARMOR_CAPABILITY);
                try {
                    ISlottedArmor armor = armorCapability.orElseThrow(IllegalStateException::new);

                    if (armor.getSlots() > 0 && event.getToolTip().get(1) != null) {
                        event.getToolTip().add(1, new StringTextComponent(armor.getTooltip()));
                    }
                } catch (IllegalStateException e) {
                }
            }
            if (item instanceof SwordItem || item instanceof AxeItem) {
                LazyOptional<ISlottedWeapon> weaponCapability = itemStack.getCapability(SlottedWeaponProvider.WEAPON_CAPABILITY);
                try {
                    ISlottedWeapon weapon = weaponCapability.orElseThrow(IllegalStateException::new);
                    if (item instanceof SwordItem) {
                        SwordItem swordItem = (SwordItem) item;
                        if (swordItem.getTier().equals(EtherGemsItemTier.MONADO)) {
                            if (!weapon.getHasInited()) {
                                weapon.init();
                            }
                        }
                    }
                    if (weapon.getSlots() > 0 && event.getToolTip().get(1) != null) {
                        String[] tooltips = weapon.getTooltip().split("\n");
                        for (int i = 0; i < tooltips.length; i++) {
                            event.getToolTip().add(i + 1, new StringTextComponent(tooltips[i]));
                        }
                    }
                } catch (IllegalStateException e) {
                }
            }
        }
    }
}
