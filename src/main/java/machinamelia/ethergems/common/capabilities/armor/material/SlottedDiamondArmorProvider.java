package machinamelia.ethergems.common.capabilities.armor.material;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import machinamelia.ethergems.common.capabilities.armor.ISlottedArmor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SlottedDiamondArmorProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(ISlottedArmor.class)
    public static final Capability<ISlottedArmor> ARMOR_CAPABILITY = null;

    private final LazyOptional<ISlottedArmor> instance = LazyOptional.of(SlottedDiamondArmorInstance::new);

    @Nonnull
    @Override
    public <T> LazyOptional <T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == ARMOR_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return getCapability(cap, null);
    }


    @Override
    public INBT serializeNBT() {
        return ARMOR_CAPABILITY.getStorage().writeNBT(ARMOR_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        ARMOR_CAPABILITY.getStorage().readNBT(ARMOR_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null, nbt);
    }
}
