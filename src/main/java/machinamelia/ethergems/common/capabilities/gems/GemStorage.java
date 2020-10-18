package machinamelia.ethergems.common.capabilities.gems;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class GemStorage implements Capability.IStorage<IGem> {
    @Override
    public INBT writeNBT(Capability<IGem> capability, IGem instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Attribute", instance.getAttribute());
        compoundNBT.putDouble("Strength", instance.getStrength());
        compoundNBT.putString("Element", instance.getElement());
        compoundNBT.putInt("Level", instance.getLevel());
        compoundNBT.putString("Tooltip", instance.getTooltipString());
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<IGem> capability, IGem instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.setAttribute(compoundNBT.getString("Attribute"));
        instance.setStrength(compoundNBT.getDouble("Strength"));
        instance.setElement(compoundNBT.getString("Element"));
        instance.setLevel(compoundNBT.getInt("Level"));
        instance.setTooltipString(compoundNBT.getString("Tooltip"));
    }
}
