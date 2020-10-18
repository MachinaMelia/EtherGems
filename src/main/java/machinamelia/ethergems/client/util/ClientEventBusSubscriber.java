package machinamelia.ethergems.client.util;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
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

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusSubscriber {
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        ScreenManager.registerFactory(ContainerInit.ETHER_FURNACE_INVENTORY_CONTAINER.get(), EtherFurnaceInventoryScreen::new);
        ScreenManager.registerFactory(ContainerInit.ETHER_FURNACE_OPTIONS_CONTAINER.get(), EtherFurnaceOptionsScreen::new);
        ScreenManager.registerFactory(ContainerInit.ETHER_FURNACE_CRAFTING_CONTAINER.get(), EtherFurnaceCraftingScreen::new);
        ScreenManager.registerFactory(ContainerInit.ETHER_FURNACE_GEM_CONFIRM_CONTAINER.get(), EtherFurnaceGemConfirmScreen::new);
        ScreenManager.registerFactory(ContainerInit.ETHER_FURNACE_CYLINDER_CONFIRM_CONTAINER.get(), EtherFurnaceCylinderConfirmScreen::new);
        ScreenManager.registerFactory(ContainerInit.CRYSTAL_INVENTORY_CONTAINER.get(), CrystalInventoryScreen::new);
        ScreenManager.registerFactory(ContainerInit.GEM_INVENTORY_CONTAINER.get(), GemInventoryScreen::new);
        ItemInit.FIRE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLAZE_ATTACK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BIND_RESIST_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLAZE_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BUFF_TIME_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.CHILL_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.EXP_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.SLOW_RESIST_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.SPIKE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.STRENGTH_DOWN_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.WEAPON_POWER_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.WATER_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.AQUATIC_CLOAK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.AUTO_HEAL_UP_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.DAMAGE_HEAL_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.HP_STEAL_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.POISON_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.DEBUFF_RESIST_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.RECOVERY_UP_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.SPIKE_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.UNBEATABLE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BACK_ATTACK_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.FIRST_ATTACK_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.PHYS_DEF_DOWN_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.ELECTRIC_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLAZE_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.CHILL_ATTACK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.CHILL_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.SLOW_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.ICE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.AERIAL_CLOAK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLEED_ATTACK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLEED_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.FALL_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.GOOD_FOOTING_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.HASTE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.WIND_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.CRITICAL_UP_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.BLEED_DEFENCE_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.PHYSICAL_PROTECT_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.DEBUFF_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.EARTH_CLOAK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.POISON_ATTACK_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.POISON_PLUS_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.EARTH_GEM.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Gem) {
                    LazyOptional<IGem> gemCapability = item.getCapability(GemProvider.GEM_CAPABILITY);
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
            }
        });
        ItemInit.FIRE_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });
        ItemInit.WATER_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });
        ItemInit.ELECTRIC_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });
        ItemInit.ICE_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });
        ItemInit.WIND_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });
        ItemInit.EARTH_CRYSTAL.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Crystal) {
                    LazyOptional<ICrystal> crystalCapability = item.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
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
            }
        });

        ItemInit.FIRE_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
        ItemInit.WATER_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
        ItemInit.ELECTRIC_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
        ItemInit.ICE_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
        ItemInit.WIND_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
        ItemInit.EARTH_CYLINDER.get().addPropertyOverride(new ResourceLocation(EtherGems.MOD_ID, "level"), new IItemPropertyGetter() {
            @Override
            public float call(ItemStack item, @Nullable World worldIn, @Nullable LivingEntity entityIn) {
                if (item.getItem() instanceof Cylinder) {
                    LazyOptional<ICylinder> cylinderCapability = item.getCapability(CylinderProvider.CYLINDER_CAPABILITY);
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
            }
        });
    }
}
