package machinamelia.ethergems.common.util;

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
