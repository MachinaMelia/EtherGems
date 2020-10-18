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

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class GemProvider implements ICapabilitySerializable<INBT> {
    @CapabilityInject(IGem.class)
    public static final Capability<IGem> GEM_CAPABILITY = null;

    private final LazyOptional<IGem> instance = LazyOptional.of(GemInstance::new);

    @Nonnull
    @Override
    public <T> LazyOptional <T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == GEM_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return getCapability(cap, null);
    }


    @Override
    public INBT serializeNBT() {
        return GEM_CAPABILITY.getStorage().writeNBT(GEM_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        GEM_CAPABILITY.getStorage().readNBT(GEM_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional is empty")), null, nbt);
    }
}
