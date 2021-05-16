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

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class EtherParticle extends SpriteTexturedParticle {
    protected EtherParticle(ClientWorld worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        float f = this.random.nextFloat() * 1.0f;
        this.setSize(0.02f, 0.02f);
        this.quadSize *= this.random.nextFloat() * 1.1f;
        this.xd *= (double) 0.02f;
        this.yd *= (double) 0.02f;
        this.zd *= (double) 0.02f;
        Random randy = new Random();
        this.setLifetime((int) (8.0d * randy.nextInt(15)));
    }

    protected void setMotion(double x, double y, double z) {
        this.xd = x;
        this.yd = y;
        this.zd = z;
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }
    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        } else {
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 1.0d;
            this.yd *= 1.0d;
            this.zd *= 1.0d;
        }
    }
}
