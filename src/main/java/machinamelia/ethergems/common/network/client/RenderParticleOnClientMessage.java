package machinamelia.ethergems.common.network.client;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RenderParticleOnClientMessage {

    public int type;
    public float x;
    public float y;
    public float z;
    public float xSpeed;
    public float ySpeed;
    public float zSpeed;
    public String uuid;

    public RenderParticleOnClientMessage(String uuid, int type, float x, float y, float z, float xSpeed, float ySpeed, float zSpeed)
    {
        messageIsValid = true;
        this.uuid = uuid;
        this.type = type;
        this.x = x;
        this.y = y;
        this.z = z;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.zSpeed = zSpeed;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public boolean SendAffinityToServerWorldMessage() {
        return messageIsValid;
    }


    public static RenderParticleOnClientMessage decode(PacketBuffer buf)
    {
        String uuid = new String(buf.readByteArray());
        int type = buf.readInt();
        float x = buf.readFloat();
        float y = buf.readFloat();
        float z = buf.readFloat();
        float xSpeed = buf.readFloat();
        float ySpeed = buf.readFloat();
        float zSpeed = buf.readFloat();
        RenderParticleOnClientMessage retval = new RenderParticleOnClientMessage(uuid, type, x, y, z, xSpeed, ySpeed, zSpeed);
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeByteArray(uuid.getBytes());
        buf.writeInt(type);
        buf.writeFloat(x);
        buf.writeFloat(y);
        buf.writeFloat(z);
        buf.writeFloat(xSpeed);
        buf.writeFloat(ySpeed);
        buf.writeFloat(zSpeed);
    }

    @Override
    public String toString()  {
        return "RenderParticleOnClientMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
