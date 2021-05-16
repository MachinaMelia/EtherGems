package machinamelia.ethergems.common.capabilities.armor;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.gems.GemInstance;
import machinamelia.ethergems.common.capabilities.gems.GemProvider;
import machinamelia.ethergems.common.capabilities.gems.IGem;
import machinamelia.ethergems.common.util.GemHandler;

public class SlottedArmorStorage implements Capability.IStorage<ISlottedArmor> {
    @Override
    public INBT writeNBT(Capability<ISlottedArmor> capability, ISlottedArmor instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (instance.getGem() != null) {
            LazyOptional<IGem> gemCapability = instance.getGem().getCapability(GemProvider.GEM_CAPABILITY);
            IGem gem = gemCapability.orElse(new GemInstance());
            if (gem != null && !gem.getAttribute().equals("") && instance.getAttribute() != null) {
                compoundNBT.putInt("Level", instance.getLevel());
                compoundNBT.putString("Attribute", instance.getAttribute());
                compoundNBT.putDouble("Strength", instance.getStrength());
                compoundNBT.putString("Element", instance.getElement());
                compoundNBT.putString("Tooltip", gem.getTooltipString());
            }
        }
        compoundNBT.putBoolean("HasInited", instance.getHasInited());
        compoundNBT.putInt("Slots", instance.getSlots());
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ISlottedArmor> capability, ISlottedArmor instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        int level = compoundNBT.getInt("Level");
        String attribute = compoundNBT.getString("Attribute");
        double strength = compoundNBT.getDouble("Strength");
        String element = compoundNBT.getString("Element");
        String tooltipString = compoundNBT.getString("Tooltip");
        ItemStack gem = GemHandler.createGem(level, element, attribute, strength);
        instance.setGem(gem);
        instance.setSlots(compoundNBT.getInt("Slots"));
        instance.setAttribute(attribute);
        instance.setElement(element);
        instance.setLevel(level);
        instance.setStrength(strength);
    }
}
