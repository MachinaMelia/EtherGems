package machinamelia.ethergems.common.init;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;

public class ParticleInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = new DeferredRegister<>(ForgeRegistries.PARTICLE_TYPES, EtherGems.MOD_ID);

    // Particles
    public static final RegistryObject<BasicParticleType> FIRE_ETHER_PARTICLE = PARTICLES.register("fire_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> WATER_ETHER_PARTICLE = PARTICLES.register("water_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> ICE_ETHER_PARTICLE = PARTICLES.register("ice_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> WIND_ETHER_PARTICLE = PARTICLES.register("wind_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> EARTH_ETHER_PARTICLE = PARTICLES.register("earth_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> ELECTRIC_ETHER_PARTICLE = PARTICLES.register("electric_ether_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> CHILL_PARTICLE = PARTICLES.register("chill_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BLOOD_PARTICLE = PARTICLES.register("blood_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BACK_ATTACK = PARTICLES.register("back_attack", () -> new BasicParticleType(true));
}
