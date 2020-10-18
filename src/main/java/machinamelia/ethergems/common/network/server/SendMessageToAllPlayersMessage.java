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

public class SendMessageToAllPlayersMessage {
    public SendMessageToAllPlayersMessage(String message)
    {
        messageIsValid = true;
        this.message = message;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    private String message;

    public String getMessage() {
        return this.message;
    }

    public static SendMessageToAllPlayersMessage decode(PacketBuffer buf)
    {
        SendMessageToAllPlayersMessage retval = new SendMessageToAllPlayersMessage(new String(buf.readByteArray()));
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        if (!messageIsValid) return;
        buf.writeByteArray(this.message.getBytes());
    }

    @Override
    public String toString()  {
        return "SendMessageToAllPlayersMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
