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
import net.minecraftforge.common.util.LazyOptional;;
import machinamelia.ethergems.common.capabilities.crystals.CrystalInstance;
import machinamelia.ethergems.common.capabilities.crystals.CrystalProvider;
import machinamelia.ethergems.common.capabilities.crystals.ICrystal;

public class OpenOptionsContainerMessage {

    private boolean messageIsValid;
    private ItemStack[] items = new ItemStack[44];

    public OpenOptionsContainerMessage(ItemStack[] items)
    {
        this.messageIsValid = true;
        this.items = items;
    }

    // not a valid way to construct the message
    private OpenOptionsContainerMessage()
    {
        messageIsValid = false;
    }

    public boolean isMessageValid() {
        return messageIsValid;
    }

    public ItemStack[] getItems() {
        return this.items;
    }

    public static OpenOptionsContainerMessage decode(PacketBuffer buf)
    {
        OpenOptionsContainerMessage retval = new OpenOptionsContainerMessage();
        retval.messageIsValid = true;
        for (int i = 0; i < 44; i++) {
            ItemStack itemStackIn = buf.readItem();
            LazyOptional<ICrystal> crystalCapabilityOut = itemStackIn.getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
            ICrystal crystalIn = crystalCapabilityOut.orElse(new CrystalInstance());
            String attributesIn = new String(buf.readByteArray());
            int levelIn = buf.readInt();
            int[] strengthsIn = buf.readVarIntArray();
            crystalIn.setAttributesCSV(attributesIn);
            crystalIn.setLevel(levelIn);
            crystalIn.setStrengthArray(strengthsIn);
            retval.items[i] = itemStackIn;
        }
        return retval;
    }

    public void encode(PacketBuffer buf)
    {
        if (!messageIsValid) return;
        for (int i = 0; i < 44; i++) {
            LazyOptional<ICrystal> crystalCapabilityOut = items[i].getCapability(CrystalProvider.CRYSTAL_CAPABILITY);
            ICrystal crystalOut = crystalCapabilityOut.orElse(new CrystalInstance());
            buf.writeItemStack(items[i], true);
            buf.writeByteArray(crystalOut.getAttributesCSV().getBytes());
            buf.writeInt(crystalOut.getLevel());
            buf.writeVarIntArray(crystalOut.getStrengthArray());
        }
    }

    @Override
    public String toString()  {
        return "OpenOptionsContainerMessage";
    }

}
