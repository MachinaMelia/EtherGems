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

import machinamelia.ethergems.common.network.NetworkHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.util.GemHandler;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.Random;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlazeEvents {
    @SubscribeEvent
    public static void blazeEvent(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntityLiving() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntityLiving();
            if (player.isBurning() && !player.getPersistentData().getBoolean("is_burning")) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Debuff Resist");
                if (fullStrength > 100.0) {
                    fullStrength = 100.0;
                }
                if (fullStrength > 0) {
                    Random randy = new Random();
                    int roll = randy.nextInt(100);
                    if (roll < fullStrength) {
                        player.getPersistentData().putBoolean("is_burning", false);
                        player.extinguish();
                        player.removePotionEffect(EffectInit.BLAZE_EFFECT.get());
                    }
                }
                fullStrength = GemHandler.getPlayerGemStrength(player, "Blaze Defence");
                if (fullStrength < -100.0) {
                    fullStrength = -100.0;
                }
                if (fullStrength == -100.0) {
                    player.getPersistentData().putBoolean("is_burning", false);
                    player.extinguish();
                    player.removePotionEffect(EffectInit.BLAZE_EFFECT.get());
                } else {
                    if (fullStrength < 0) {
                        int oldTimer = player.getFireTimer();
                        int newTimer = (int) (oldTimer + ((fullStrength / 100.0) * oldTimer));
                        player.setFireTimer(newTimer);
                    }
                    int timer = player.getFireTimer();
                    if (timer < 0) {
                        timer = 0;
                    }
                    player.getPersistentData().putBoolean("is_burning", true);
                    player.addPotionEffect(new EffectInstance(EffectInit.BLAZE_EFFECT.get(), timer));
                }
            }
            if (!player.isBurning()) {
                player.getPersistentData().putBoolean("is_burning", false);
                player.removePotionEffect(EffectInit.BLAZE_EFFECT.get());
            }
        }
        LivingEntity entity = event.getEntityLiving();
        if (entity.getActivePotionEffect(EffectInit.BIND_EFFECT.get()) == null) {
            if (entity instanceof MobEntity) {
                MobEntity mob = (MobEntity) entity;
                mob.setNoAI(false);
            }
        }
    }
}
