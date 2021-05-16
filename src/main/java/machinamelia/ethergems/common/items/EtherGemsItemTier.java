package machinamelia.ethergems.common.items;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.LazyValue;
import machinamelia.ethergems.common.init.ItemInit;

import java.util.function.Supplier;

public enum EtherGemsItemTier implements IItemTier {

    MONADO(3, Integer.MAX_VALUE, 12.0F, 4.0F, 22, () -> {
        return Ingredient.of(ItemInit.FIRE_GEM.get());
    });

    private final int level;
    private final int uses;
    private final int enchantmentValue;
    private final float speed;
    private final float attackDamageBonus;
    private final LazyValue<Ingredient> repairMaterial;

    private EtherGemsItemTier(int level, int uses, float speed, float attackDamageBonus, int enchantmentValue, Supplier<Ingredient> repairMaterial) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairMaterial.get();
    }
}
