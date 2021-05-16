package machinamelia.ethergems.common.effects.debuffs;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.passive.DolphinEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.SendParticleToServerWorldMessage;

import java.util.Random;

public class ChillEffect extends Effect {
    public ChillEffect() {
        super(EffectType.HARMFUL, 00000000);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (!(entity instanceof DolphinEntity || entity instanceof PolarBearEntity || entity instanceof WolfEntity || entity instanceof StrayEntity || entity instanceof WitherSkeletonEntity)) {
            if (!entity.getPersistentData().contains("chill_counter")) {
                entity.getPersistentData().putInt("chill_counter", 40);
            }
            if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if (player.getPersistentData().getInt("chill_time") <= 0) {
                    return;
                }
            }
            Random randy = new Random();
            if (entity.level.isClientSide && entity.getPersistentData().getInt("chill_counter") < 40) {
                if (entity.getPersistentData().getInt("chill_counter") % 3 == 2) {
                    SendParticleToServerWorldMessage sendParticleToServerWorldMessage = new SendParticleToServerWorldMessage(1, (float) entity.getX(), (float) (entity.getY() + 0.5 + randy.nextDouble()), (float) entity.getZ(), (float) (0.33 * 0.04 * randy.nextDouble()), (float) (0.33 * 0.04 * randy.nextDouble()), (float) (0.33 * 0.04 * randy.nextDouble()));
                    NetworkHandler.simpleChannel.sendToServer(sendParticleToServerWorldMessage);
                }
                if (entity.getPersistentData().getInt("chill_counter") % 3 == 1) {
                    SendParticleToServerWorldMessage sendParticleToServerWorldMessage = new SendParticleToServerWorldMessage(1, (float) entity.getX(), (float) (entity.getY() + 0.5 + randy.nextDouble()), (float) entity.getZ(), (float) (-0.33 * 0.04 * randy.nextDouble()), (float) (0.33 * 0.04 * randy.nextDouble()), (float) (0.33 * 0.04 * randy.nextDouble()));
                    NetworkHandler.simpleChannel.sendToServer(sendParticleToServerWorldMessage);
                }
                if (entity.getPersistentData().getInt("chill_counter") % 3 == 0) {
                    SendParticleToServerWorldMessage sendParticleToServerWorldMessage = new SendParticleToServerWorldMessage(1, (float) entity.getX(), (float) (entity.getY() + 0.5 + randy.nextDouble()), (float) entity.getZ(), (float) (-0.33 * 0.04 * randy.nextDouble()), (float) (-0.33 * 0.04 * randy.nextDouble()), (float) (0.33 * 0.04 * randy.nextDouble()));
                    NetworkHandler.simpleChannel.sendToServer(sendParticleToServerWorldMessage);
                }
            }
            if (entity.getPersistentData().getInt("chill_counter") < 0) {
                entity.hurt(DamageSource.MAGIC, 1.0F);
                entity.getPersistentData().putInt("chill_counter", 40);
            } else {
                int newCounter = entity.getPersistentData().getInt("chill_counter");
                newCounter--;
                entity.getPersistentData().putInt("chill_counter", newCounter);
            }
        } else {
            entity.removeEffect(EffectInit.CHILL_EFFECT.get());
        }
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
