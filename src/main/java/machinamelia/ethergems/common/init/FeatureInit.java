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

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.TableLootEntry;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.world.feature.structures.OseTowerPieces;
import machinamelia.ethergems.common.world.feature.structures.OseTowerStructure;

import java.util.Locale;

public class FeatureInit {

    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<Feature<?>>(ForgeRegistries.FEATURES, EtherGems.MOD_ID);

    public static IStructurePieceType OSE_TOWER_PIECE = OseTowerPieces.Piece::new;
    public static final RegistryObject<OseTowerStructure> OSE_TOWER = FEATURES.register("ose_tower", () -> new OseTowerStructure(NoFeatureConfig::deserialize));
    @SubscribeEvent
    public static void registerStructurePieces(RegistryEvent.Register<Feature<?>> event) {
        Registry.register(Registry.STRUCTURE_PIECE, "ose_tower", OSE_TOWER_PIECE);
    }

    @SubscribeEvent
    public static void onVanillaLootLoading(LootTableLoadEvent event)
    {
        if (event.getName().equals(new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest")))
        {
            event.getTable().addPool(LootPool.builder().addEntry(TableLootEntry.builder(new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest"))).build());
        }

    }
}
