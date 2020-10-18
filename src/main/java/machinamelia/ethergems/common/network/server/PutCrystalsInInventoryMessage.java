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

public class PutCrystalsInInventoryMessage {

    private boolean messageIsValid;
    private ItemStack[] items = new ItemStack[42];

    public PutCrystalsInInventoryMessage(ItemStack[] items)
    {
        this.messageIsValid = true;
        this.items = items;
    }

    // not a valid way to construct the message
    private PutCrystalsInInventoryMessage()
    {
        messageIsValid = false;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public static PutCrystalsInInventoryMessage decode(PacketBuffer buf)
    {
        int length = buf.readInt();
        ItemStack[] items = new ItemStack[length];
        for (int i = 0; i < length; i++) {
            ItemStack itemStackIn = buf.readItemStack();
            items[i] = itemStackIn;

        }
        PutCrystalsInInventoryMessage retval = new PutCrystalsInInventoryMessage(items);
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        if (!messageIsValid) return;
        buf.writeInt(items.length);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                buf.writeItemStack(items[i]);
            }
        }
    }

    @Override
    public String toString()  {
        return "PutCrystalsInInventoryMessage";
    }

}
