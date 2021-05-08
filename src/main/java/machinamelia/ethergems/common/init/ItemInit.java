package machinamelia.ethergems.common.init;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.items.EtherGemsItemTier;
import machinamelia.ethergems.common.items.armor.material.SlottedArmor;
import machinamelia.ethergems.common.items.armor.material.SlottedDyeableArmor;
import machinamelia.ethergems.common.items.crystals.*;
import machinamelia.ethergems.common.items.cylinders.*;
import machinamelia.ethergems.common.items.gems.*;
import machinamelia.ethergems.common.items.weapon.SlottedAxe;
import machinamelia.ethergems.common.items.weapon.SlottedSword;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, EtherGems.MOD_ID);

    // Items
    public static final RegistryObject<Item> FIRE_CRYSTAL = ITEMS.register("fire_crystal", FireCrystal::new);
    public static final RegistryObject<Item> WATER_CRYSTAL = ITEMS.register("water_crystal", WaterCrystal::new);
    public static final RegistryObject<Item> ICE_CRYSTAL = ITEMS.register("ice_crystal", IceCrystal::new);
    public static final RegistryObject<Item> WIND_CRYSTAL = ITEMS.register("wind_crystal", WindCrystal::new);
    public static final RegistryObject<Item> EARTH_CRYSTAL = ITEMS.register("earth_crystal", EarthCrystal::new);
    public static final RegistryObject<Item> ELECTRIC_CRYSTAL = ITEMS.register("electric_crystal", ElectricCrystal::new);
    public static final RegistryObject<Item> PURE_FIRE_CRYSTAL = ITEMS.register("pure_fire_crystal", ItemBase::new);
    public static final RegistryObject<Item> PURE_WATER_CRYSTAL = ITEMS.register("pure_water_crystal", ItemBase::new);
    public static final RegistryObject<Item> PURE_ICE_CRYSTAL = ITEMS.register("pure_ice_crystal", ItemBase::new);
    public static final RegistryObject<Item> PURE_WIND_CRYSTAL = ITEMS.register("pure_wind_crystal", ItemBase::new);
    public static final RegistryObject<Item> PURE_EARTH_CRYSTAL = ITEMS.register("pure_earth_crystal", ItemBase::new);
    public static final RegistryObject<Item> PURE_ELECTRIC_CRYSTAL = ITEMS.register("pure_electric_crystal", ItemBase::new);
    public static final RegistryObject<Item> FIRE_GEM = ITEMS.register("fire_gem", FireGem::new);
    public static final RegistryObject<Item> BLAZE_ATTACK_GEM = ITEMS.register("blaze_attack_gem", BlazeAttackGem::new);
    public static final RegistryObject<Item> BIND_RESIST_GEM = ITEMS.register("bind_resist_gem", BindResistGem::new);
    public static final RegistryObject<Item> BLAZE_PLUS_GEM = ITEMS.register("blaze_plus_gem", BlazePlusGem::new);
    public static final RegistryObject<Item> BUFF_TIME_PLUS_GEM = ITEMS.register("buff_time_plus_gem", BuffTimePlusGem::new);
    public static final RegistryObject<Item> CHILL_DEFENCE_GEM = ITEMS.register("chill_defence_gem", ChillDefenceGem::new);
    public static final RegistryObject<Item> EXP_GEM = ITEMS.register("exp_gem", EXPGem::new);
    public static final RegistryObject<Item> SLOW_RESIST_GEM = ITEMS.register("slow_resist_gem", SlowResistGem::new);
    public static final RegistryObject<Item> SPIKE_GEM = ITEMS.register("spike_gem", SpikeGem::new);
    public static final RegistryObject<Item> STRENGTH_DOWN_GEM = ITEMS.register("strength_down_gem", StrengthDownGem::new);
    public static final RegistryObject<Item> WEAPON_POWER_GEM = ITEMS.register("weapon_power_gem", WeaponPowerGem::new);
    public static final RegistryObject<Item> WATER_GEM = ITEMS.register("water_gem", WaterGem::new);
    public static final RegistryObject<Item> AQUATIC_CLOAK_GEM = ITEMS.register("aquatic_cloak_gem", AquaticCloakGem::new);
    public static final RegistryObject<Item> AUTO_HEAL_UP_GEM = ITEMS.register("auto_heal_up_gem", AutoHealUpGem::new);
    public static final RegistryObject<Item> DAMAGE_HEAL_GEM = ITEMS.register("damage_heal_gem", DamageHealGem::new);
    public static final RegistryObject<Item> DEBUFF_RESIST_GEM = ITEMS.register("debuff_resist_gem", DebuffResistGem::new);
    public static final RegistryObject<Item> HP_STEAL_GEM = ITEMS.register("hp_steal_gem", HPStealGem::new);
    public static final RegistryObject<Item> POISON_DEFENCE_GEM = ITEMS.register("poison_defence_gem", PoisonDefenceGem::new);
    public static final RegistryObject<Item> RECOVERY_UP_GEM = ITEMS.register("recovery_up_gem", RecoveryUpGem::new);
    public static final RegistryObject<Item> SPIKE_DEFENCE_GEM = ITEMS.register("spike_defence_gem", SpikeDefenceGem::new);
    public static final RegistryObject<Item> UNBEATABLE_GEM = ITEMS.register("unbeatable_gem", UnbeatableGem::new);
    public static final RegistryObject<Item> BACK_ATTACK_PLUS_GEM = ITEMS.register("back_attack_plus_gem", BackAttackPlusGem::new);
    public static final RegistryObject<Item> FIRST_ATTACK_PLUS_GEM = ITEMS.register("first_attack_plus_gem", FirstAttackPlusGem::new);
    public static final RegistryObject<Item> PHYS_DEF_DOWN_GEM = ITEMS.register("phys_def_down_gem", PhysDefDownGem::new);
    public static final RegistryObject<Item> ELECTRIC_GEM = ITEMS.register("electric_gem", ElectricGem::new);
    public static final RegistryObject<Item> BLAZE_DEFENCE_GEM = ITEMS.register("blaze_defence_gem", BlazeDefenceGem::new);
    public static final RegistryObject<Item> CHILL_ATTACK_GEM = ITEMS.register("chill_attack_gem", ChillAttackGem::new);
    public static final RegistryObject<Item> SLOW_GEM = ITEMS.register("slow_gem", SlowGem::new);
    public static final RegistryObject<Item> CHILL_PLUS_GEM = ITEMS.register("chill_plus_gem", ChillPlusGem::new);
    public static final RegistryObject<Item> ICE_GEM = ITEMS.register("ice_gem", IceGem::new);
    public static final RegistryObject<Item> AERIAL_CLOAK_GEM = ITEMS.register("aerial_cloak_gem", AerialCloakGem::new);
    public static final RegistryObject<Item> BLEED_ATTACK_GEM = ITEMS.register("bleed_attack_gem", BleedAttackGem::new);
    public static final RegistryObject<Item> BLEED_PLUS_GEM = ITEMS.register("bleed_plus_gem", BleedPlusGem::new);
    public static final RegistryObject<Item> FALL_DEFENCE_GEM = ITEMS.register("fall_defence_gem", FallDefenceGem::new);
    public static final RegistryObject<Item> GOOD_FOOTING_GEM = ITEMS.register("good_footing_gem", GoodFootingGem::new);
    public static final RegistryObject<Item> HASTE_GEM = ITEMS.register("haste_gem", HasteGem::new);
    public static final RegistryObject<Item> WIND_GEM = ITEMS.register("wind_gem", WindGem::new);
    public static final RegistryObject<Item> BLEED_DEFENCE_GEM = ITEMS.register("bleed_defence_gem", BleedDefenceGem::new);
    public static final RegistryObject<Item> CRITICAL_UP_GEM = ITEMS.register("critical_up_gem", CriticalUpGem::new);
    public static final RegistryObject<Item> EARTH_CLOAK_GEM = ITEMS.register("earth_cloak_gem", EarthCloakGem::new);
    public static final RegistryObject<Item> PHYSICAL_PROTECT_GEM = ITEMS.register("physical_protect_gem", PhysicalProtectGem::new);
    public static final RegistryObject<Item> DEBUFF_PLUS_GEM = ITEMS.register("debuff_plus_gem", DebuffPlusGem::new);
    public static final RegistryObject<Item> POISON_ATTACK_GEM = ITEMS.register("poison_attack_gem", PoisonAttackGem::new);
    public static final RegistryObject<Item> POISON_PLUS_GEM = ITEMS.register("poison_plus_gem", PoisonPlusGem::new);
    public static final RegistryObject<Item> EARTH_GEM = ITEMS.register("earth_gem", EarthGem::new);
    public static final RegistryObject<Item> FIRE_CYLINDER = ITEMS.register("fire_cylinder", FireCylinder::new);
    public static final RegistryObject<Item> WATER_CYLINDER = ITEMS.register("water_cylinder", WaterCylinder::new);
    public static final RegistryObject<Item> ICE_CYLINDER = ITEMS.register("ice_cylinder", IceCylinder::new);
    public static final RegistryObject<Item> WIND_CYLINDER = ITEMS.register("wind_cylinder", WindCylinder::new);
    public static final RegistryObject<Item> EARTH_CYLINDER = ITEMS.register("earth_cylinder", EarthCylinder::new);
    public static final RegistryObject<Item> ELECTRIC_CYLINDER = ITEMS.register("electric_cylinder", ElectricCylinder::new);
    public static final RegistryObject<SlottedDyeableArmor> SLOTTED_LEATHER_HELMET = ITEMS.register("slotted_leather_helmet", () -> new SlottedDyeableArmor(ArmorMaterial.LEATHER, EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final RegistryObject<SlottedDyeableArmor> SLOTTED_LEATHER_CHESTPLATE = ITEMS.register("slotted_leather_chestplate", () -> new SlottedDyeableArmor(ArmorMaterial.LEATHER, EquipmentSlotType.CHEST, (new Item.Properties())));
    public static final RegistryObject<SlottedDyeableArmor> SLOTTED_LEATHER_LEGGINGS = ITEMS.register("slotted_leather_leggings", () -> new SlottedDyeableArmor(ArmorMaterial.LEATHER, EquipmentSlotType.LEGS, (new Item.Properties())));
    public static final RegistryObject<SlottedDyeableArmor> SLOTTED_LEATHER_BOOTS = ITEMS.register("slotted_leather_boots", () -> new SlottedDyeableArmor(ArmorMaterial.LEATHER, EquipmentSlotType.FEET, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_IRON_HELMET = ITEMS.register("slotted_iron_helmet", () -> new SlottedArmor(ArmorMaterial.IRON, EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_IRON_CHESTPLATE = ITEMS.register("slotted_iron_chestplate", () -> new SlottedArmor(ArmorMaterial.IRON, EquipmentSlotType.CHEST, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_IRON_LEGGINGS = ITEMS.register("slotted_iron_leggings", () -> new SlottedArmor(ArmorMaterial.IRON, EquipmentSlotType.LEGS, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_IRON_BOOTS = ITEMS.register("slotted_iron_boots", () -> new SlottedArmor(ArmorMaterial.IRON, EquipmentSlotType.FEET, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_GOLDEN_HELMET = ITEMS.register("slotted_golden_helmet", () -> new SlottedArmor(ArmorMaterial.GOLD, EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_GOLDEN_CHESTPLATE = ITEMS.register("slotted_golden_chestplate", () -> new SlottedArmor(ArmorMaterial.GOLD, EquipmentSlotType.CHEST, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_GOLDEN_LEGGINGS = ITEMS.register("slotted_golden_leggings", () -> new SlottedArmor(ArmorMaterial.GOLD, EquipmentSlotType.LEGS, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_GOLDEN_BOOTS = ITEMS.register("slotted_golden_boots", () -> new SlottedArmor(ArmorMaterial.GOLD, EquipmentSlotType.FEET, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_DIAMOND_HELMET = ITEMS.register("slotted_diamond_helmet", () -> new SlottedArmor(ArmorMaterial.DIAMOND, EquipmentSlotType.HEAD, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_DIAMOND_CHESTPLATE = ITEMS.register("slotted_diamond_chestplate", () -> new SlottedArmor(ArmorMaterial.DIAMOND, EquipmentSlotType.CHEST, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_DIAMOND_LEGGINGS = ITEMS.register("slotted_diamond_leggings", () -> new SlottedArmor(ArmorMaterial.DIAMOND, EquipmentSlotType.LEGS, (new Item.Properties())));
    public static final RegistryObject<SlottedArmor> SLOTTED_DIAMOND_BOOTS = ITEMS.register("slotted_diamond_boots", () -> new SlottedArmor(ArmorMaterial.DIAMOND, EquipmentSlotType.FEET, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> SLOTTED_WOODEN_SWORD = ITEMS.register("slotted_wooden_sword", () -> new SlottedSword(ItemTier.WOOD, 3, -2.4F, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> SLOTTED_STONE_SWORD = ITEMS.register("slotted_stone_sword", () -> new SlottedSword(ItemTier.STONE, 3, -2.4F, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> SLOTTED_IRON_SWORD = ITEMS.register("slotted_iron_sword", () -> new SlottedSword(ItemTier.IRON, 3, -2.4F, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> SLOTTED_GOLDEN_SWORD = ITEMS.register("slotted_golden_sword", () -> new SlottedSword(ItemTier.GOLD, 3, -2.4F, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> SLOTTED_DIAMOND_SWORD = ITEMS.register("slotted_diamond_sword", () -> new SlottedSword(ItemTier.DIAMOND, 3, -2.4F, (new Item.Properties())));
    public static final RegistryObject<SlottedAxe> SLOTTED_WOODEN_AXE = ITEMS.register("slotted_wooden_axe", () -> new SlottedAxe(ItemTier.WOOD, 6.0F, -3.2F, (new Item.Properties())));
    public static final RegistryObject<SlottedAxe> SLOTTED_STONE_AXE = ITEMS.register("slotted_stone_axe", () -> new SlottedAxe(ItemTier.STONE, 7.0F, -3.2F, (new Item.Properties())));
    public static final RegistryObject<SlottedAxe> SLOTTED_IRON_AXE = ITEMS.register("slotted_iron_axe", () -> new SlottedAxe(ItemTier.IRON, 6.0F, -3.1F, (new Item.Properties())));
    public static final RegistryObject<SlottedAxe> SLOTTED_GOLDEN_AXE = ITEMS.register("slotted_golden_axe", () -> new SlottedAxe(ItemTier.GOLD, 6.0F, -3.0F, (new Item.Properties())));
    public static final RegistryObject<SlottedAxe> SLOTTED_DIAMOND_AXE = ITEMS.register("slotted_diamond_axe", () -> new SlottedAxe(ItemTier.DIAMOND, 5.0F, -3.0F, (new Item.Properties())));
    public static final RegistryObject<SlottedSword> MONADO = ITEMS.register("monado", () -> new SlottedSword(EtherGemsItemTier.MONADO, 4, -2.4F, (new Item.Properties())));

}
