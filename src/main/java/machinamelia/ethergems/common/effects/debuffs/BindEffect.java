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
import net.minecraft.entity.MobEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

public class BindEffect extends Effect {
    public BindEffect() {
        super(EffectType.HARMFUL, 4738376);
    }
    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof MobEntity) {
            MobEntity mob = (MobEntity) entity;
            mob.setNoAi(true);
        }
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
