package machinamelia.ethergems.common.items;

/*
 *   Copyright (C) 2020 MachinaMelia
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
        return Ingredient.fromItems(ItemInit.FIRE_GEM.get());
    });

    private final int harvestLevel;
    private final int maxUses;
    private final int enchanablility;
    private final float efficiency;
    private final float attackDamage;
    private final LazyValue<Ingredient> repairMaterial;

    private EtherGemsItemTier(int harvestLevel, int maxUses, float efficiency, float attackDamage, int enchantibility, Supplier<Ingredient> repairMaterial) {
        this.harvestLevel = harvestLevel;
        this.maxUses = maxUses;
        this.efficiency = efficiency;
        this.attackDamage = attackDamage;
        this.enchanablility = enchantibility;
        this.repairMaterial = new LazyValue<>(repairMaterial);
    }

    @Override
    public int getMaxUses() {
        return this.maxUses;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchanablility;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return this.repairMaterial.getValue();
    }
}
