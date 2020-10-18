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

public class SendPotionEffectToClientMessage {

    public int duration;
    public int amplifier;
    public String uuid;
    public String effectName;

    public SendPotionEffectToClientMessage(String uuid, String effectName, int duration, int amplifier)
    {
        messageIsValid = true;
        this.uuid = uuid;
        this.effectName = effectName;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }


    public static SendPotionEffectToClientMessage decode(PacketBuffer buf)
    {
        String uuid = new String(buf.readByteArray());
        String effectName = new String(buf.readByteArray());
        int duration = buf.readInt();
        int amplifier = buf.readInt();
        SendPotionEffectToClientMessage retval = new SendPotionEffectToClientMessage(uuid, effectName, duration, amplifier);
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeByteArray(uuid.getBytes());
        buf.writeByteArray(effectName.getBytes());
        buf.writeInt(duration);
        buf.writeInt(amplifier);
    }

    @Override
    public String toString()  {
        return "SendPotionEffectToClientMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
