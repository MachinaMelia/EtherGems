package machinamelia.ethergems.common.init;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.effects.buffs.HasteEffect;
import machinamelia.ethergems.common.effects.buffs.ResistanceEffect;
import machinamelia.ethergems.common.effects.buffs.SpeedEffect;
import machinamelia.ethergems.common.effects.debuffs.*;

public class EffectInit {

    public static final DeferredRegister<Effect> EFFECTS = new DeferredRegister<>(ForgeRegistries.POTIONS, EtherGems.MOD_ID);

    // Effects
    public static final RegistryObject<Effect> STRENGTH_DOWN_EFFECT = EFFECTS.register("strength_down_effect", () -> new StrengthDownEffect().addAttributesModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<Effect> BIND_EFFECT = EFFECTS.register("bind_effect", () -> new BindEffect().addAttributesModifier(SharedMonsterAttributes.FLYING_SPEED, "adb7391f-fbcf-4e20-bf44-c84268babd6e", (double)-150.0F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-50.0F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> FOLLOW_RANGE_EFFECT = EFFECTS.register("follow_range_effect", () -> new FollowRangeEffect(-0.5D).addAttributesModifier(SharedMonsterAttributes.FOLLOW_RANGE, "87f6b7f5-9841-4200-88af-c3900d44a539", -1.0D, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<Effect> PHYS_DEF_DOWN_EFFECT = EFFECTS.register("phys_def_down_effect", () -> new PhysDefDownEffect());
    public static final RegistryObject<Effect> BLAZE_EFFECT = EFFECTS.register("blaze_effect", () -> new BlazeEffect());
    public static final RegistryObject<Effect> CHILL_EFFECT = EFFECTS.register("chill_effect", () -> new ChillEffect());
    public static final RegistryObject<Effect> BLEED_EFFECT = EFFECTS.register("bleed_effect", () -> new BleedEffect());
    public static final RegistryObject<Effect> HASTE_EFFECT = EFFECTS.register("haste_effect", () -> new HasteEffect().addAttributesModifier(SharedMonsterAttributes.ATTACK_SPEED, "AF8B6E3F-3328-4C0A-AA36-5BA2BB9DBEF3", (double)0.01F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> SPEED_EFFECT = EFFECTS.register("speed_effect", () -> new SpeedEffect().addAttributesModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.01F, AttributeModifier.Operation.MULTIPLY_TOTAL));
    public static final RegistryObject<Effect> RESISTANCE_EFFECT = EFFECTS.register("resistance_effect", () -> new ResistanceEffect(1.0D, 00000000, false).addAttributesModifier(SharedMonsterAttributes.ARMOR, "db3b4394-d71b-4aa3-a0c0-8ff269ac6725", 1.0, AttributeModifier.Operation.ADDITION));
    public static final RegistryObject<Effect> PHYSICAL_PROTECT_EFFECT = EFFECTS.register("physical_protect_effect", () -> new ResistanceEffect(1.0D, 14000672, true).addAttributesModifier(SharedMonsterAttributes.ARMOR, "db3b4394-d71b-4aa3-a0c0-8ff269ac6725", 1.0, AttributeModifier.Operation.ADDITION));
}
