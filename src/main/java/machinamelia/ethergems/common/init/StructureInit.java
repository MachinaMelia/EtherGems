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

import com.google.common.collect.ImmutableMap;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.world.feature.structures.OseTowerPieces;
import machinamelia.ethergems.common.world.feature.structures.OseTowerStructure;

import java.util.Map;

public class StructureInit {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, EtherGems.MOD_ID);

    public static IStructurePieceType OSE_TOWER_PIECE = OseTowerPieces.Piece::new;
    public static final RegistryObject<Structure<NoFeatureConfig>> OSE_TOWER = STRUCTURES.register("ose_tower", () -> new OseTowerStructure(NoFeatureConfig.CODEC));

    public static void registerStructurePieces() {
        Registry.register(Registry.STRUCTURE_PIECE, "ose_tower", OSE_TOWER_PIECE);
    }

    public static void setupStructures() {
        setupMapSpacingAndLand(
                OSE_TOWER.get(), /* The instance of the structure */
                new StructureSeparationSettings(15,
                        7,
                        696840419));
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(
            F structure,
            StructureSeparationSettings structureSeparationSettings) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);
        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();
            if(!(structureMap instanceof ImmutableMap)){
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }

    @SubscribeEvent
    public static void onVanillaLootLoading(LootTableLoadEvent event)
    {
        if (event.getName().equals(new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest")))
        {
            event.getTable().addPool(LootPool.lootPool().add(TableLootEntry.lootTableReference(new ResourceLocation(EtherGems.MOD_ID, "chests/monado_chest"))).build());
        }

    }
}
