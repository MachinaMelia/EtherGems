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

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SendArmorGemToServerMessage {

    public boolean isCompatible;
    public int index;
    public ItemStack armorGem;

    public SendArmorGemToServerMessage(ItemStack armorGem, int index, boolean isCompatible)
    {
        messageIsValid = true;
        this.index = index;
        this.armorGem = armorGem;
        this.isCompatible = isCompatible;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public boolean SendArmorGemToServerMessage() {
        return messageIsValid;
    }


    public static SendArmorGemToServerMessage decode(PacketBuffer buf)
    {
        boolean isCompatible = buf.readBoolean();
        ItemStack armorGem = buf.readItemStack();
        int index = buf.readInt();
        SendArmorGemToServerMessage retval = new SendArmorGemToServerMessage(armorGem, index, isCompatible);
        retval.messageIsValid = true;
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeBoolean(isCompatible);
        buf.writeItemStack(armorGem);
        buf.writeInt(index);
    }

    @Override
    public String toString()  {
        return "SendArmorGemToServerMessage";
    }

    private boolean messageIsValid;

    private static final Logger LOGGER = LogManager.getLogger();
}
