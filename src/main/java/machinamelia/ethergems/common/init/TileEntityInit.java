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

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.tileentity.EtherFurnaceTileEntity;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = new DeferredRegister<>(ForgeRegistries.TILE_ENTITIES, EtherGems.MOD_ID);

    // Tile Entity
    public static final RegistryObject<TileEntityType<EtherFurnaceTileEntity>> ETHER_FURNACE_TILE_ENTITY = TILE_ENTITY_TYPES.register("ether_furnace_tile_entity", () -> TileEntityType.Builder.create(EtherFurnaceTileEntity::new, BlockInit.ETHER_FURNACE.get()).build(null));
}
