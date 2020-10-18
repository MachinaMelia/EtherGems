package machinamelia.ethergems.common.capabilities.world;

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

public class CrystalLevelStorage implements Capability.IStorage<ICrystalLevel> {
    @Override
    public INBT writeNBT(Capability<ICrystalLevel> capability, ICrystalLevel instance, Direction side)
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("crystal_level", instance.getCrystalLevel());
        return compoundNBT;
    }

    @Override
    public void readNBT(Capability<ICrystalLevel> capability, ICrystalLevel instance, Direction side, INBT nbt)
    {
        CompoundNBT compoundNBT = (CompoundNBT) nbt;
        instance.setCrystalLevel(compoundNBT.getInt("crystal_level"));
    }
}
