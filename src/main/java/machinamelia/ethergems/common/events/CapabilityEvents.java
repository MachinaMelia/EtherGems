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

import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.capabilities.armor.SlottedArmorProvider;
import machinamelia.ethergems.common.capabilities.armor.material.SlottedDiamondArmorProvider;
import machinamelia.ethergems.common.capabilities.armor.material.SlottedGoldenArmorProvider;
import machinamelia.ethergems.common.capabilities.armor.material.SlottedIronArmorProvider;
import machinamelia.ethergems.common.capabilities.armor.material.SlottedLeatherArmorProvider;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.elemental.*;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.weapons.SlottedWeaponProvider;
import machinamelia.ethergems.common.capabilities.weapons.material.*;
import machinamelia.ethergems.common.items.EtherGemsItemTier;
import machinamelia.ethergems.common.items.crystals.*;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.items.gems.Gem;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityEvents {

    public static final ResourceLocation CRYSTAL_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "crystal");
    public static final ResourceLocation GEM_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "gem");
    public static final ResourceLocation CYLINDER_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "cylinder");
    public static final ResourceLocation ARMOR_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "armor");
    public static final ResourceLocation WEAPON_CAPABILITY = new ResourceLocation(EtherGems.MOD_ID, "weapon");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<ItemStack> event) {
        Item item = event.getObject().getItem();
        ItemStack itemStack = event.getObject();

        if (item instanceof Crystal) {
            if (item instanceof FireCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new FireCrystalProvider());
            } else if (item instanceof WaterCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new WaterCrystalProvider());
            } else if (item instanceof ElectricCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new ElectricCrystalProvider());
            } else if (item instanceof IceCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new IceCrystalProvider());
            } else if (item instanceof WindCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new WindCrystalProvider());
            } else if (item instanceof EarthCrystal) {
                event.addCapability(CRYSTAL_CAPABILITY, new EarthCrystalProvider());
            } else {
                event.addCapability(CRYSTAL_CAPABILITY, new CrystalProvider());
            }
        }
        if (item instanceof Gem) {
            event.addCapability(GEM_CAPABILITY, new GemProvider());
        }

        if (item instanceof Cylinder) {
            event.addCapability(CYLINDER_CAPABILITY, new CylinderProvider());
        }

        if (item instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) item;
            IArmorMaterial material = armor.getArmorMaterial();
            if (material.equals(ArmorMaterial.LEATHER)) {
                event.addCapability(ARMOR_CAPABILITY, new SlottedLeatherArmorProvider());
            } else if (material.equals(ArmorMaterial.IRON)) {
                event.addCapability(ARMOR_CAPABILITY, new SlottedIronArmorProvider());
            } else if (material.equals(ArmorMaterial.GOLD)) {
                event.addCapability(ARMOR_CAPABILITY, new SlottedGoldenArmorProvider());
            } else if (material.equals(ArmorMaterial.DIAMOND)) {
                event.addCapability(ARMOR_CAPABILITY, new SlottedDiamondArmorProvider());
            } else {
                event.addCapability(ARMOR_CAPABILITY, new SlottedArmorProvider());
            }
        }
        if (item instanceof SwordItem) {
            SwordItem weapon = (SwordItem) item;
            IItemTier material = weapon.getTier();
            if (material.equals(ItemTier.WOOD)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedWoodenWeaponProvider());
            } else if (material.equals(ItemTier.STONE)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedStoneWeaponProvider());
            } else if (material.equals(ItemTier.IRON)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedIronWeaponProvider());
            } else if (material.equals(ItemTier.GOLD)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedGoldWeaponProvider());
            } else if (material.equals(ItemTier.DIAMOND)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedDiamondWeaponProvider());
            } else if (material.equals(EtherGemsItemTier.MONADO)) {
                event.addCapability(WEAPON_CAPABILITY, new MonadoProvider());
            } else {
                event.addCapability(WEAPON_CAPABILITY, new SlottedWeaponProvider());
            }
        }
        if (item instanceof AxeItem) {
            AxeItem weapon = (AxeItem) item;
            IItemTier material = weapon.getTier();
            if (material.equals(ItemTier.WOOD)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedWoodenWeaponProvider());
            } else if (material.equals(ItemTier.STONE)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedStoneWeaponProvider());
            } else if (material.equals(ItemTier.IRON)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedIronWeaponProvider());
            } else if (material.equals(ItemTier.GOLD)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedGoldWeaponProvider());
            } else if (material.equals(ItemTier.DIAMOND)) {
                event.addCapability(WEAPON_CAPABILITY, new SlottedDiamondWeaponProvider());
            } else {
                event.addCapability(WEAPON_CAPABILITY, new SlottedWeaponProvider());
            }
        }
    }
}
