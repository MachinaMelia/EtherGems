package machinamelia.ethergems.client.util;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import machinamelia.ethergems.client.screens.*;
import machinamelia.ethergems.common.EtherGems;

import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderInstance;
import machinamelia.ethergems.common.capabilities.cylinders.CylinderProvider;
import machinamelia.ethergems.common.capabilities.cylinders.ICylinder;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.init.ContainerInit;
import machinamelia.ethergems.common.init.ItemInit;
import machinamelia.ethergems.common.items.crystals.Crystal;
import machinamelia.ethergems.common.items.cylinders.Cylinder;
import machinamelia.ethergems.common.items.gems.Gem;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.register(ContainerInit.ETHER_FURNACE_INVENTORY_CONTAINER.get(), EtherFurnaceInventoryScreen::new);
        ScreenManager.register(ContainerInit.ETHER_FURNACE_OPTIONS_CONTAINER.get(), EtherFurnaceOptionsScreen::new);
        ScreenManager.register(ContainerInit.ETHER_FURNACE_CRAFTING_CONTAINER.get(), EtherFurnaceCraftingScreen::new);
        ScreenManager.register(ContainerInit.ETHER_FURNACE_GEM_CONFIRM_CONTAINER.get(), EtherFurnaceGemConfirmScreen::new);
        ScreenManager.register(ContainerInit.ETHER_FURNACE_CYLINDER_CONFIRM_CONTAINER.get(), EtherFurnaceCylinderConfirmScreen::new);
        ScreenManager.register(ContainerInit.CRYSTAL_INVENTORY_CONTAINER.get(), CrystalInventoryScreen::new);
        ScreenManager.register(ContainerInit.GEM_INVENTORY_CONTAINER.get(), GemInventoryScreen::new);
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.FIRE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLAZE_ATTACK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BIND_RESIST_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLAZE_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BUFF_TIME_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.CHILL_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.EXP_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.SLOW_RESIST_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.SPIKE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.STRENGTH_DOWN_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WEAPON_POWER_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WATER_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.AQUATIC_CLOAK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.AUTO_HEAL_UP_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.DAMAGE_HEAL_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.HP_STEAL_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.POISON_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.DEBUFF_RESIST_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.RECOVERY_UP_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.SPIKE_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.UNBEATABLE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BACK_ATTACK_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.FIRST_ATTACK_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.PHYS_DEF_DOWN_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ELECTRIC_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLAZE_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.CHILL_ATTACK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.CHILL_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.SLOW_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ICE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.AERIAL_CLOAK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLEED_ATTACK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLEED_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.FALL_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.GOOD_FOOTING_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.HASTE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WIND_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.CRITICAL_UP_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.BLEED_DEFENCE_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.PHYSICAL_PROTECT_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.DEBUFF_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.EARTH_CLOAK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.POISON_ATTACK_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.POISON_PLUS_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.EARTH_GEM.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = stack.getCapability(GemProvider.GEM_CAPABILITY);
                    IGem gemInstance = gemCapability.orElse(new GemInstance());
                    switch (gemInstance.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                        case 6:
                            return 0.9f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.FIRE_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });

        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WATER_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ELECTRIC_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ICE_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WIND_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.EARTH_CRYSTAL.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = stack.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
                    ICrystal crystal = crystalCapability.orElse(new CrystalInstance());
                    switch (crystal.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.FIRE_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WATER_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ELECTRIC_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.ICE_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.WIND_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
        event.enqueueWork(() -> {
            ItemModelsProperties.register(ItemInit.EARTH_CYLINDER.get(), new ResourceLocation(EtherGems.MOD_ID, "level"), (stack, world, living) -> {
                if (stack.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = stack.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
                    ICylinder cylinder = cylinderCapability.orElse(new CylinderInstance());
                    switch (cylinder.getLevel()) {
                        case 1:
                            return 0.15f;
                        case 2:
                            return 0.3f;
                        case 3:
                            return 0.45f;
                        case 4:
                            return 0.6f;
                        case 5:
                            return 0.75f;
                    }
                }
                return 0.0f;
            });
        });
    }
}
