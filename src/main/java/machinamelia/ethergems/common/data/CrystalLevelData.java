package machinamelia.ethergems.common.data;

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
import net.minecraft.world.storage.WorldSavedData;
import machinamelia.ethergems.common.EtherGems;

public class CrystalLevelData extends WorldSavedData {

    private static final String DATA_NAME = EtherGems.MOD_ID + "_crystal_data";

    private boolean dirty;
    public int crystalLevel = 0;

    public CrystalLevelData() {
        super(DATA_NAME);
    }

    @Override
    public void read(CompoundNBT nbt) {
        this.crystalLevel = nbt.getInt("crystal_level");
    }

    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        nbt.putInt("crystal_level", this.crystalLevel);
        return nbt;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }
}
