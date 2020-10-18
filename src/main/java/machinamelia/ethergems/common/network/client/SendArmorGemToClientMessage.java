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

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendArmorGemToClientMessage {


    public int index;
    public String uuid;
    public ItemStack armorGem;

    public SendArmorGemToClientMessage(String uuid, ItemStack armorGem, int index)
    {
        messageIsValid = true;
        this.uuid = uuid;
        this.index = index;
        this.armorGem = armorGem;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }


    public static SendArmorGemToClientMessage decode(PacketBuffer buf)
    {
        String uuid = new String(buf.readByteArray());
        ItemStack armorGem = buf.readItemStack();
        int index = buf.readInt();
        SendArmorGemToClientMessage retval = new SendArmorGemToClientMessage(uuid, armorGem, index);
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeByteArray(uuid.getBytes());
        buf.writeItemStack(armorGem);
        buf.writeInt(index);
    }

    @Override
    public String toString()  {
        return "SenArmorGemToClientMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
