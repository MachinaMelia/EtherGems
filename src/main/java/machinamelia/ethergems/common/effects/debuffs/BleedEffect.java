package machinamelia.ethergems.common.effects.debuffs;

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
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.StrayEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.network.NetworkHandler;
import machinamelia.ethergems.common.network.server.SendParticleToServerWorldMessage;

import java.util.Random;

public class BleedEffect extends Effect {
    public BleedEffect() {
        super(EffectType.HARMFUL, 00000000);
    }
    @Override
    public void performEffect(LivingEntity entity, int amplifier) {
        if (!(entity instanceof SkeletonHorseEntity || entity instanceof IronGolemEntity || entity instanceof SkeletonEntity || entity instanceof StrayEntity || entity instanceof WitherSkeletonEntity)) {
            if (!entity.getPersistentData().contains("bleed_counter")) {
                entity.getPersistentData().putInt("bleed_counter", 40);
            }
            Random randy = new Random();
            if (entity.world.isRemote) {
                if (entity.getPersistentData().getInt("bleed_counter") > 0 && entity.getPersistentData().getInt("bleed_counter") % 10 == 0) {
                    SendParticleToServerWorldMessage sendParticleToServerWorldMessage = new SendParticleToServerWorldMessage(0, (float) entity.getPosX(), (float) (entity.getPosY() + randy.nextDouble()), (float) entity.getPosZ(), 0.0f, 0.0f, 0.0f);
                    NetworkHandler.simpleChannel.sendToServer(sendParticleToServerWorldMessage);
                }
            }
            if (entity.getPersistentData().getInt("bleed_counter") > 0) {
                entity.attackEntityFrom(DamageSource.MAGIC, 1.0F);
                entity.getPersistentData().putInt("bleed_counter", 40);
            } else {
                int newCounter = entity.getPersistentData().getInt("bleed_counter");
                newCounter--;
                entity.getPersistentData().putInt("bleed_counter", newCounter);
            }
        } else {
            entity.removePotionEffect(EffectInit.BLEED_EFFECT.get());
        }
    }
    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
