package machinamelia.ethergems.common.init;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.blocks.BlockItemBase;
import machinamelia.ethergems.common.blocks.EtherFurnace;
import machinamelia.ethergems.common.blocks.elemental.*;
import machinamelia.ethergems.common.blocks.pure.*;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, EtherGems.MOD_ID);
    // Blocks
    public static final RegistryObject<Block> FIRE_DEPOSIT = BLOCKS.register("fire_deposit", FireDeposit::new);
    public static final RegistryObject<Block> WATER_DEPOSIT = BLOCKS.register("water_deposit", WaterDeposit::new);
    public static final RegistryObject<Block> ICE_DEPOSIT = BLOCKS.register("ice_deposit", IceDeposit::new);
    public static final RegistryObject<Block> WIND_DEPOSIT = BLOCKS.register("wind_deposit", WindDeposit::new);
    public static final RegistryObject<Block> EARTH_DEPOSIT = BLOCKS.register("earth_deposit", EarthDeposit::new);
    public static final RegistryObject<Block> ELECTRIC_DEPOSIT = BLOCKS.register("electric_deposit", ElectricDeposit::new);
    public static final RegistryObject<Block> PURE_FIRE_DEPOSIT = BLOCKS.register("pure_fire_deposit", PureFireDeposit::new);
    public static final RegistryObject<Block> PURE_WATER_DEPOSIT = BLOCKS.register("pure_water_deposit", PureWaterDeposit::new);
    public static final RegistryObject<Block> PURE_ICE_DEPOSIT = BLOCKS.register("pure_ice_deposit", PureIceDeposit::new);
    public static final RegistryObject<Block> PURE_WIND_DEPOSIT = BLOCKS.register("pure_wind_deposit", PureWindDeposit::new);
    public static final RegistryObject<Block> PURE_EARTH_DEPOSIT = BLOCKS.register("pure_earth_deposit", PureEarthDeposit::new);
    public static final RegistryObject<Block> PURE_ELECTRIC_DEPOSIT = BLOCKS.register("pure_electric_deposit", PureElectricDeposit::new);
    public static final RegistryObject<Block> ETHER_FURNACE = BLOCKS.register("ether_furnace", EtherFurnace::new);

    // Block Items
    public static final RegistryObject<Item> FIRE_DEPOSIT_ITEM = ItemInit.ITEMS.register("fire_deposit", () -> new BlockItemBase(FIRE_DEPOSIT.get()));
    public static final RegistryObject<Item> WATER_DEPOSIT_ITEM = ItemInit.ITEMS.register("water_deposit", () -> new BlockItemBase(WATER_DEPOSIT.get()));
    public static final RegistryObject<Item> ICE_DEPOSIT_ITEM = ItemInit.ITEMS.register("ice_deposit", () -> new BlockItemBase(ICE_DEPOSIT.get()));
    public static final RegistryObject<Item> WIND_DEPOSIT_ITEM = ItemInit.ITEMS.register("wind_deposit", () -> new BlockItemBase(WIND_DEPOSIT.get()));
    public static final RegistryObject<Item> EARTH_DEPOSIT_ITEM = ItemInit.ITEMS.register("earth_deposit", () -> new BlockItemBase(EARTH_DEPOSIT.get()));
    public static final RegistryObject<Item> ELECTRIC_DEPOSIT_ITEM = ItemInit.ITEMS.register("electric_deposit", () -> new BlockItemBase(ELECTRIC_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_FIRE_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_fire_deposit", () -> new BlockItemBase(PURE_FIRE_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_WATER_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_water_deposit", () -> new BlockItemBase(PURE_WATER_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_ICE_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_ice_deposit", () -> new BlockItemBase(PURE_ICE_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_WIND_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_wind_deposit", () -> new BlockItemBase(PURE_WIND_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_EARTH_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_earth_deposit", () -> new BlockItemBase(PURE_EARTH_DEPOSIT.get()));
    public static final RegistryObject<Item> PURE_ELECTRIC_DEPOSIT_ITEM = ItemInit.ITEMS.register("pure_electric_deposit", () -> new BlockItemBase(PURE_ELECTRIC_DEPOSIT.get()));
    public static final RegistryObject<Item> ETHER_FURNACE_ITEM = ItemInit.ITEMS.register("ether_furnace", () -> new BlockItemBase(ETHER_FURNACE.get()));

}
