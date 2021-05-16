package machinamelia.ethergems.common.effects.buffs;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class HasteEffect extends Effect {
    public HasteEffect() {
        super(EffectType.BENEFICIAL, 00000000);
    }

    @Override
    public boolean shouldRender(EffectInstance effect) { return false; }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) { return false; }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
