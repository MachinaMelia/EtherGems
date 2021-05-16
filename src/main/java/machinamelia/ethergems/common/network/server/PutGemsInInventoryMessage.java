package machinamelia.ethergems.common.network.server;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;

public class PutGemsInInventoryMessage {

    private boolean messageIsValid;
    private ItemStack[] items = new ItemStack[42];

    public PutGemsInInventoryMessage(ItemStack[] items)
    {
        this.messageIsValid = true;
        this.items = items;
    }

    // not a valid way to construct the message
    private PutGemsInInventoryMessage()
    {
        messageIsValid = false;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public static PutGemsInInventoryMessage decode(PacketBuffer buf)
    {
        int length = buf.readInt();
        ItemStack[] items = new ItemStack[length];
        for (int i = 0; i < length; i++) {
            ItemStack itemStackIn = buf.readItem();
            items[i] = itemStackIn;

        }
        PutGemsInInventoryMessage retval = new PutGemsInInventoryMessage(items);
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        if (!messageIsValid) return;
        buf.writeInt(items.length);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                buf.writeItemStack(items[i], true);
            } else {
                buf.writeItemStack(ItemStack.EMPTY, true);
            }
        }
    }

    @Override
    public String toString()  {
        return "PutGemsInInventoryMessage";
    }

}
