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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.fish.PufferfishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.util.GemHandler;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FollowRangeEvents {
    @SubscribeEvent
    public static void followRangeEvent(LivingSetAttackTargetEvent event) {
        if (event.getTarget() instanceof PlayerEntity && event.getEntityLiving().getActivePotionEffect(EffectInit.FOLLOW_RANGE_EFFECT.get()) == null) {
            PlayerEntity player = (PlayerEntity) event.getTarget();
            LivingEntity entity = event.getEntityLiving();
            int baseRange = 16;
            if (entity instanceof ZombieEntity) {
                if (entity instanceof ZombiePigmanEntity) {
                    baseRange = 35;
                } else if (entity instanceof DrownedEntity) {
                    baseRange = 20;
                } else if (entity instanceof ZombieVillagerEntity) {
                    baseRange = 40;
                } else if (entity instanceof HuskEntity){
                    baseRange = 40;
                } else {
                    baseRange = 40;
                }
            } else if (entity instanceof BlazeEntity) {
                baseRange = 48;
            } else if (entity instanceof GhastEntity) {
                baseRange = 64;
            } else if (entity instanceof PillagerEntity) {
                baseRange = 64;
            } else if (entity instanceof  RavagerEntity) {
                baseRange = 32;
            } else if (entity instanceof EvokerEntity) {
                baseRange = 12;
            } else if (entity instanceof VindicatorEntity) {
                baseRange = 12;
            }

            if (entity instanceof DrownedEntity || entity instanceof GuardianEntity || entity instanceof PufferfishEntity || entity instanceof DolphinEntity) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Aquatic Cloak");
                if (fullStrength > 60.0) {
                    fullStrength = 60.0;
                }
                if (fullStrength > 0) {
                    entity.addPotionEffect(new EffectInstance(EffectInit.FOLLOW_RANGE_EFFECT.get(), 160, (int) ((fullStrength / 100.0) * baseRange)));
                }
            } else if (entity instanceof BeeEntity || entity instanceof  GhastEntity || entity instanceof EnderDragonEntity || entity instanceof VexEntity || entity instanceof PhantomEntity || entity instanceof BlazeEntity) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Aerial Cloak");
                if (fullStrength > 60.0) {
                    fullStrength = 60.0;
                }
                if (fullStrength > 0) {
                    entity.addPotionEffect(new EffectInstance(EffectInit.FOLLOW_RANGE_EFFECT.get(), 160, (int) ((fullStrength / 100.0) * baseRange)));
                }
            } else {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Earth Cloak");
                if (fullStrength > 60.0) {
                    fullStrength = 60.0;
                }
                if (fullStrength > 0) {
                    entity.addPotionEffect(new EffectInstance(EffectInit.FOLLOW_RANGE_EFFECT.get(), 160, (int) ((fullStrength / 100.0) * baseRange)));
                }
            }
        }
    }

}
