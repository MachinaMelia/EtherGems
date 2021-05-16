package machinamelia.ethergems.client.util;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SweepAttackParticle;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.ParticleInit;
import machinamelia.ethergems.common.particles.*;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Bus.MOD)
public class ParticleEventBusSubscriber {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particleEngine.register(ParticleInit.FIRE_ETHER_PARTICLE.get(), FireEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.WATER_ETHER_PARTICLE.get(), WaterEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ICE_ETHER_PARTICLE.get(), IceEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.WIND_ETHER_PARTICLE.get(), WindEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.EARTH_ETHER_PARTICLE.get(), EarthEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.ELECTRIC_ETHER_PARTICLE.get(), ElectricEtherParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.CHILL_PARTICLE.get(), ChillParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.BLOOD_PARTICLE.get(), BloodParticle.Factory::new);
        Minecraft.getInstance().particleEngine.register(ParticleInit.BACK_ATTACK.get(), SweepAttackParticle.Factory::new);
    }
}
