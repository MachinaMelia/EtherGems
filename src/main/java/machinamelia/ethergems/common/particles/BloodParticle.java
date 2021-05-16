package machinamelia.ethergems.common.particles;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BloodParticle extends SpriteTexturedParticle
{
    public BloodParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(world, x, y, z, 0.0, 0.0, 0.0);
        this.rCol = 0.529F;
        this.gCol = 0.0F;
        this.bCol = 0.078F;
        this.gravity = 1.0F;
        this.quadSize *= 2.0F;
        this.setLifetime((int) (4.0F / (this.random.nextFloat() * 0.9F + 0.1F)) + 20);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;
        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            BloodParticle bloodParticle = new BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            bloodParticle.setSpriteFromAge(this.spriteSet);
            return bloodParticle;
        }
    }
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
}