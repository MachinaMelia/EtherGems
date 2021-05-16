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

import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.container.*;

public class ContainerInit {

    public static final DeferredRegister<ContainerType<?>> CONTAINER_TYPES = DeferredRegister.create(ForgeRegistries.CONTAINERS, EtherGems.MOD_ID);

    // Containers
    public static final RegistryObject<ContainerType<EtherFurnaceInventoryContainer>> ETHER_FURNACE_INVENTORY_CONTAINER = CONTAINER_TYPES.register("ether_furnace_inventory_container", () -> IForgeContainerType.create(EtherFurnaceInventoryContainer::new));
    public static final RegistryObject<ContainerType<EtherFurnaceOptionsContainer>> ETHER_FURNACE_OPTIONS_CONTAINER = CONTAINER_TYPES.register("ether_furnace_options_container", () -> IForgeContainerType.create(EtherFurnaceOptionsContainer::new));
    public static final RegistryObject<ContainerType<EtherFurnaceCraftingContainer>> ETHER_FURNACE_CRAFTING_CONTAINER = CONTAINER_TYPES.register("ether_furnace_crafting_container", () -> IForgeContainerType.create(EtherFurnaceCraftingContainer::new));
    public static final RegistryObject<ContainerType<EtherFurnaceGemConfirmContainer>> ETHER_FURNACE_GEM_CONFIRM_CONTAINER = CONTAINER_TYPES.register("ether_furnace_gem_confirm_container", () -> IForgeContainerType.create(EtherFurnaceGemConfirmContainer::new));
    public static final RegistryObject<ContainerType<EtherFurnaceCylinderConfirmContainer>> ETHER_FURNACE_CYLINDER_CONFIRM_CONTAINER = CONTAINER_TYPES.register("ether_furnace_cylinder_confirm_container", () -> IForgeContainerType.create(EtherFurnaceCylinderConfirmContainer::new));
    public static final RegistryObject<ContainerType<CrystalInventoryContainer>> CRYSTAL_INVENTORY_CONTAINER = CONTAINER_TYPES.register("crystal_inventory_container", () -> IForgeContainerType.create(CrystalInventoryContainer::new));
    public static final RegistryObject<ContainerType<GemInventoryContainer>> GEM_INVENTORY_CONTAINER = CONTAINER_TYPES.register("gem_inventory_container", () -> IForgeContainerType.create(GemInventoryContainer::new));
}
