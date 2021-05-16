package machinamelia.ethergems.common.util;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import machinamelia.ethergems.common.EtherGems;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {
    public static final ClientConfig CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static boolean compatCrafting;
    static {
        final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
        CLIENT_SPEC = specPair.getRight();
        CLIENT = specPair.getLeft();
    }
    public static void bakeConfig() {
        compatCrafting = CLIENT.compatCrafting.get();
    }
   public static class ClientConfig {

        public final ForgeConfigSpec.BooleanValue compatCrafting;

        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.push("category");
            compatCrafting = builder.comment("Adds alternative crafting recipes for slotted armor and weapons for greater mod compatibility").translation(EtherGems.MOD_ID + ".config." + "compatCrafting").define("compatCrafting", false);
            builder.pop();
        }
   }

    public static void init() {}
}
