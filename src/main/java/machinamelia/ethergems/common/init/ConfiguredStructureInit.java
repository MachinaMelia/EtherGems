package machinamelia.ethergems.common.init;

/*
 *   Copyright (C) 2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.EtherGems;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructureInit {

    public static StructureFeature<?, ?> CONFIGURED_OSE_TOWER = StructureInit.OSE_TOWER.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(EtherGems.MOD_ID, "configured_ose_tower"), CONFIGURED_OSE_TOWER);
    }
}
