package machinamelia.ethergems.common.capabilities.crystals;

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

public class CrystalStorage implements Capability.IStorage<ICrystal> {
    @Override
    public INBT writeNBT(Capability<ICrystal> capability, ICrystal instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("Attributes", instance.getAttributesCSV());
        compoundNBT.putIntArray("Strengths", instance.getStrengthArray());
        compoundNBT.putInt("Level", instance.getLevel());
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ICrystal> capability, ICrystal instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.setAttributesCSV(compoundNBT.getString("Attributes"));
        instance.setStrengthArray(compoundNBT.getIntArray("Strengths"));
        instance.setLevel(compoundNBT.getInt("Level"));
    }
}
