package machinamelia.ethergems.common.effects.buffs;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;

public class ResistanceEffect extends Effect {

    protected final double bonusPerLevel;

    private boolean shouldRender;

    public ResistanceEffect(double bonusPerLevel, int color, boolean shouldRender) {
        super(EffectType.HARMFUL, color);
        this.bonusPerLevel = bonusPerLevel;
        this.shouldRender = shouldRender;
    }
    public double getAttributeModifierAmount(int amplifier, AttributeModifier modifier) {
        return this.bonusPerLevel * (double)(amplifier + 1);
    }

    @Override
    public boolean shouldRender(EffectInstance effect) { return shouldRender; }

    @Override
    public boolean shouldRenderHUD(EffectInstance effect) { return shouldRender; }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
