package machinamelia.ethergems.common.network.server;

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

public class SendAffinityToServerWorldMessage {

    public int maxAffinity;
    public String uuid;

    public SendAffinityToServerWorldMessage(String uuid, int maxAffinity)
    {
        messageIsValid = true;
        this.uuid = uuid;
        this.maxAffinity = maxAffinity;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public boolean SendAffinityToServerWorldMessage() {
        return messageIsValid;
    }


    public static SendAffinityToServerWorldMessage decode(PacketBuffer buf)
    {
        String uuid = new String(buf.readByteArray());
        int maxAffinity = buf.readInt();
        SendAffinityToServerWorldMessage retval = new SendAffinityToServerWorldMessage(uuid, maxAffinity);
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeByteArray(uuid.getBytes());
        buf.writeInt(maxAffinity);
    }

    @Override
    public String toString()  {
        return "SendAffinityToServerWorldMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
