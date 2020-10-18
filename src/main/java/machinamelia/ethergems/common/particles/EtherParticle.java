package machinamelia.ethergems.common.particles;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class EtherParticle extends SpriteTexturedParticle {
    protected EtherParticle(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        float f = this.rand.nextFloat() * 1.0f;
        this.setSize(0.02f, 0.02f);
        this.particleScale *= this.rand.nextFloat() * 1.1f;
        this.motionX *= (double) 0.02f;
        this.motionY *= (double) 0.02f;
        this.motionZ *= (double) 0.02f;
        Random randy = new Random();
        this.setMaxAge((int) (8.0d * randy.nextInt(15)));
    }

    protected void setMotion(double x, double y, double z) {
        this.motionX = x;
        this.motionY = y;
        this.motionZ = z;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    @Override
    public void tick() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        if (this.maxAge-- <= 0) {
            this.setExpired();
        } else {
            this.move(this.motionX, this.motionY, this.motionZ);
            this.motionX *= 1.0d;
            this.motionY *= 1.0d;
            this.motionZ *= 1.0d;
        }
    }
}
