package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020-2021 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.StriderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import machinamelia.ethergems.common.EtherGems;
import machinamelia.ethergems.common.init.EffectInit;
import machinamelia.ethergems.common.util.GemHandler;
import machinamelia.ethergems.common.util.MaxHealthHandler;

import java.util.Random;

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EffectEvents {

    private static int clientTickCounter = 20;
    private static int entityTickCounter = 40;
    private static int entityTickCounter2 = 40;
    private static int entityTickCounter3 = 40;

    @SubscribeEvent
    public static void giveEffects(PlayerEvent event) {
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            double fullStrength = GemHandler.getPlayerGemStrength(player, "HP Up");
            if (fullStrength > 0) {
                MaxHealthHandler.updateHealthModifier(player, fullStrength);
            } else {
                MaxHealthHandler.updateHealthModifier(player, 0);
            }
            fullStrength = GemHandler.getPlayerGemStrength(player, "Haste");
            if (fullStrength > 50.0) {
                fullStrength = 50.0;
            }
            if (fullStrength > 0) {
                player.addEffect(new EffectInstance(EffectInit.HASTE_EFFECT.get(), 160, (int) fullStrength));
            }
            fullStrength = GemHandler.getPlayerGemStrength(player, "Quick Step");
            if (fullStrength > 25.0) {
                fullStrength = 25.0;
            }
            if (fullStrength > 0) {
                player.addEffect(new EffectInstance(EffectInit.SPEED_EFFECT.get(), 160, (int) fullStrength));
            }
            fullStrength = GemHandler.getPlayerGemStrength(player, "Muscle Up");
            if (fullStrength > 3) {
                fullStrength = 3;
            }
            if (fullStrength > 0) {
                player.addEffect(new EffectInstance(EffectInit.RESISTANCE_EFFECT.get(), 160, (int) (fullStrength / 0.5)));
            }
        }
    }
    @SubscribeEvent
    public static void checkEffectTime(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.getPersistentData().getInt("poison_time") > 0 && event.player.getPersistentData().getBoolean("shouldCheckPoisonTime")) {
            int poisonTime = event.player.getPersistentData().getInt("poison_time");
            poisonTime--;
            event.player.getPersistentData().putInt("poison_time", poisonTime);
            event.player.getPersistentData().putBoolean("shouldCheckPoisonTime", !event.player.getPersistentData().getBoolean("shouldCheckPoisonTime"));
        } else {
            event.player.getPersistentData().putBoolean("shouldCheckPoisonTime", !event.player.getPersistentData().getBoolean("shouldCheckPoisonTime"));
        }
        if (event.player != null && event.player.getPersistentData().getInt("chill_time") > 0 && event.player.getPersistentData().getBoolean("shouldCheckChillTime")) {
            int chillTime = event.player.getPersistentData().getInt("chill_time");
            chillTime--;
            event.player.getPersistentData().putInt("chill_time", chillTime);
            event.player.getPersistentData().putBoolean("shouldCheckChillTime", !event.player.getPersistentData().getBoolean("shouldCheckChillTime"));
        } else {
            event.player.getPersistentData().putBoolean("shouldCheckChillTime", !event.player.getPersistentData().getBoolean("shouldCheckChillTime"));
        }
        if (event.player != null && event.player.getPersistentData().getInt("bleed_time") > 0 && event.player.getPersistentData().getBoolean("shouldCheckBleedTime")) {
            int bleedTime = event.player.getPersistentData().getInt("bleed_time");
            bleedTime--;
            event.player.getPersistentData().putInt("bleed_time", bleedTime);
            event.player.getPersistentData().putBoolean("shouldCheckBleedTime", !event.player.getPersistentData().getBoolean("shouldCheckBleedTime"));
        } else {
            event.player.getPersistentData().putBoolean("shouldCheckBleedTime", !event.player.getPersistentData().getBoolean("shouldCheckBleedTime"));
        }
        if (event.player != null && event.player.getPersistentData().getInt("physical_protect_time") > 0 && event.player.getPersistentData().getBoolean("shouldCheckPhysicalProtectTime")) {
            int physicalProtectTime = event.player.getPersistentData().getInt("physical_protect_time");
            physicalProtectTime--;
            if (physicalProtectTime < 0) {
                physicalProtectTime = -1;
            }
            event.player.getPersistentData().putInt("physical_protect_time", physicalProtectTime);
            event.player.getPersistentData().putBoolean("shouldCheckPhysicalProtectTime", !event.player.getPersistentData().getBoolean("shouldCheckPhysicalProtectTime"));
        } else {
            event.player.getPersistentData().putBoolean("shouldCheckPhysicalProtectTime", !event.player.getPersistentData().getBoolean("shouldCheckPhysicalProtectTime"));
        }
    }

    @SubscribeEvent
    public static void performDOTEffects(LivingEvent.LivingUpdateEvent event) {
        EffectInstance chillEffect = event.getEntityLiving().getEffect(EffectInit.CHILL_EFFECT.get());
        if (chillEffect != null) {
            chillEffect.applyEffect(event.getEntityLiving());
        }
        EffectInstance bleedEffect = event.getEntityLiving().getEffect(EffectInit.BLEED_EFFECT.get());
        if (bleedEffect != null) {
            bleedEffect.applyEffect(event.getEntityLiving());
        }
    }
    @SubscribeEvent
    public static void causeExtraEffectDamage(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (!(livingEntity instanceof BlazeEntity || livingEntity instanceof ZombifiedPiglinEntity || livingEntity instanceof ZoglinEntity || livingEntity instanceof StriderEntity || livingEntity instanceof MagmaCubeEntity || livingEntity instanceof WitherSkeletonEntity)) {
                double fullStrength = entity.getPersistentData().getDouble("blaze_plus_damage");
                if (fullStrength > 0.0 && entityTickCounter <= 0) {
                    if (livingEntity.isOnFire()) {
                        livingEntity.hurt(DamageSource.ON_FIRE, (float) fullStrength);
                    } else {
                        livingEntity.getPersistentData().putDouble("blaze_plus_damage", 0.0);
                    }
                    entityTickCounter = 20;
                } else {
                    entityTickCounter--;
                }
            }
            double fullStrength = entity.getPersistentData().getDouble("chill_plus_damage");
            if (fullStrength > 0.0 && entityTickCounter2 <= 0) {
                if (livingEntity.getEffect(EffectInit.CHILL_EFFECT.get()) != null) {
                    livingEntity.hurt(DamageSource.MAGIC, (float) fullStrength);
                } else {
                    livingEntity.getPersistentData().putDouble("chill_plus_damage", 0.0);
                }
                entityTickCounter2 = 20;
            } else {
                entityTickCounter2--;
            }
            fullStrength = entity.getPersistentData().getDouble("bleed_plus_damage");
            if (fullStrength > 0.0 && entityTickCounter3 <= 0) {
                if (livingEntity.getEffect(EffectInit.BLEED_EFFECT.get()) != null) {
                    livingEntity.hurt(DamageSource.MAGIC, (float) fullStrength);
                } else {
                    livingEntity.getPersistentData().putDouble("bleed_plus_damage", 0.0);
                }
                entityTickCounter3 = 20;
            } else {
                entityTickCounter3--;
            }
        }
    }
    @SubscribeEvent
    public static void potionEffectEvent(PotionEvent.PotionAddedEvent event) {
        if (event.getEntity() instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            if (event.getPotionEffect().getDescriptionId().equals("effect.minecraft.slowness") && event.getEntity() instanceof PlayerEntity) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Slow Resist");
                if (fullStrength > 100.0) {
                    fullStrength = 100.0;
                }
                double weaponPower = GemHandler.getPlayerGemStrength(player, "Weapon Power");
                if (weaponPower > 50.0) {
                    weaponPower = 50.0;
                }
                Random randy = new Random();
                int roll = randy.nextInt(100);
                if (roll < fullStrength) {
                    player.getPersistentData().putBoolean("shouldRemoveSlowness", true);
                } else {
                    player.getPersistentData().putBoolean("shouldRemoveSlowness", false);
                }
            }
            if (event.getPotionEffect().getDescriptionId().equals("effect.ethergems.bind_effect")) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Bind Resist");
                if (fullStrength > 100.0) {
                    fullStrength = 100.0;
                }
                Random randy = new Random();
                int roll = randy.nextInt(100);
                if (roll < fullStrength) {
                    player.getPersistentData().putBoolean("shouldRemoveBind", true);
                } else {
                    player.getPersistentData().putBoolean("shouldRemoveBind", false);
                }
            }
            if ((event.getPotionEffect().getDescriptionId().equals("effect.minecraft.slowness") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.wither") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.unluck") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.hunger") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.weakness") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.mining_fatigue") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.nausea") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.blindness") || event.getPotionEffect().getDescriptionId().equals("effect.ethergems.bind_effect") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.poison") || event.getPotionEffect().getDescriptionId().equals("effect.ethergems.chill_effect") || event.getPotionEffect().getDescriptionId().equals("effect.ethergems.bleed_effect") || event.getPotionEffect().getDescriptionId().equals("effect.ethergems.strength_down_effect") || event.getPotionEffect().getDescriptionId().equals("effect.ethergems.phys_def_down_effect"))) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Debuff Resist");
                Random randy = new Random();
                int roll = randy.nextInt(100);
                if (roll < fullStrength) {
                    player.getPersistentData().putString("remove_debuff", event.getPotionEffect().getDescriptionId());
                } else {
                    player.getPersistentData().putString("remove_debuff", "");
                }
            }
            if (event.getPotionEffect().getDescriptionId().equals("effect.minecraft.poison")) {
                if (player.getPersistentData().getInt("poison_time") <= 0) {
                    double fullStrength = GemHandler.getPlayerGemStrength(player, "Poison Defence");
                    if (fullStrength < -100.0) {
                        fullStrength = -100.0;
                    }
                    if (fullStrength < 0) {
                        int oldDuration = event.getPotionEffect().getDuration();
                        int newDuration = oldDuration + (int) ((oldDuration) * (fullStrength / 100.0));
                        if (newDuration < 10) {
                            newDuration = 10;
                        }
                        player.getPersistentData().putInt("poison_time", newDuration);
                        player.getPersistentData().putBoolean("is_poisoned", true);
                    } else {
                        player.getPersistentData().putInt("poison_time", event.getPotionEffect().getDuration());
                        player.getPersistentData().putBoolean("is_poisoned", true);
                    }
                }
            }
            if (event.getPotionEffect().getDescriptionId().equals("effect.ethergems.chill_effect")) {
                if (player.getPersistentData().getInt("chill_time") <= 0) {
                    double fullStrength = GemHandler.getPlayerGemStrength(player, "Chill Defence");
                    if (fullStrength < -100.0) {
                        fullStrength = -100.0;
                    }
                    if (fullStrength < 0) {
                        int oldDuration = event.getPotionEffect().getDuration();
                        int newDuration = oldDuration + (int) ((oldDuration) * (fullStrength / 100.0));
                        if (newDuration < 10) {
                            newDuration = 10;
                        }
                        player.getPersistentData().putInt("chill_time", newDuration);
                        player.getPersistentData().putBoolean("is_chilled", true);
                    } else {
                        player.getPersistentData().putInt("chill_time", event.getPotionEffect().getDuration());
                        player.getPersistentData().putBoolean("is_chilled", true);
                    }
                }
            }
            if (event.getPotionEffect().getDescriptionId().equals("effect.ethergems.bleed_effect")) {
                if (player.getPersistentData().getInt("bleed_time") <= 0) {
                    double fullStrength = GemHandler.getPlayerGemStrength(player, "Bleed Defence");
                    if (fullStrength < -100.0) {
                        fullStrength = -100.0;
                    }
                    if (fullStrength < 0) {
                        int oldDuration = event.getPotionEffect().getDuration();
                        int newDuration = oldDuration + (int) ((oldDuration) * (fullStrength / 100.0));
                        if (newDuration < 10) {
                            newDuration = 10;
                        }
                        player.getPersistentData().putInt("bleed_time", newDuration);
                        player.getPersistentData().putBoolean("is_bleeding", true);
                    } else {
                        player.getPersistentData().putInt("bleed_time", event.getPotionEffect().getDuration());
                        player.getPersistentData().putBoolean("is_bleeding", true);
                    }
                }
            }
            if (event.getPotionEffect().getDescriptionId().equals("effect.minecraft.speed") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.haste") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.strength") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.jump_boost") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.regeneration") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.resistance") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.fire_resistance") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.water_breathing") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.invisibility") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.night_vision") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.absorption") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.luck") || event.getPotionEffect().getDescriptionId().equals("effect.minecraft.slow_falling")) {
                if (!player.level.isClientSide) {
                    double buffTimePlus = GemHandler.getPlayerGemStrength(player, "Buff Time Plus");
                    if (buffTimePlus > 150.0) {
                        buffTimePlus = 150.0;
                    }
                    if (buffTimePlus > 9 && player.getPersistentData().getString("remove_buff").equals("")) {
                        player.getPersistentData().putString("remove_buff", event.getPotionEffect().getDescriptionId());
                        player.getPersistentData().putInt("buff_duration", (int) (event.getPotionEffect().getDuration() * (buffTimePlus / 100.0)));
                    }
                }
            }

        }
    }
    @SubscribeEvent
    public static void removeBuffs(PlayerEvent event) {
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            if (!player.level.isClientSide) {
                String buff = player.getPersistentData().getString("remove_buff");
                int buffTime = player.getPersistentData().getInt("buff_duration");
                if (buffTime > 0) {
                    switch (buff) {
                        case "effect.minecraft.speed":
                            EffectInstance oldEffect = player.getEffect(Effects.MOVEMENT_SPEED);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.MOVEMENT_SPEED);
                                    player.addEffect(new EffectInstance(Effects.MOVEMENT_SPEED, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.haste":
                            oldEffect = player.getEffect(Effects.DIG_SPEED);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.DIG_SPEED);
                                    player.addEffect(new EffectInstance(Effects.DIG_SPEED, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.strength":
                            oldEffect = player.getEffect(Effects.DAMAGE_BOOST);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.DAMAGE_BOOST);
                                    ;
                                    player.addEffect(new EffectInstance(Effects.DAMAGE_BOOST, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.jump_boost":
                            oldEffect = player.getEffect(Effects.JUMP);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.JUMP);
                                    ;
                                    player.addEffect(new EffectInstance(Effects.JUMP, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.regeneration":
                            oldEffect = player.getEffect(Effects.REGENERATION);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.REGENERATION);
                                    player.addEffect(new EffectInstance(Effects.REGENERATION, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.resistance":
                            oldEffect = player.getEffect(Effects.DAMAGE_RESISTANCE);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.DAMAGE_RESISTANCE);
                                    player.addEffect(new EffectInstance(Effects.DAMAGE_RESISTANCE, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.fire_resistance":
                            oldEffect = player.getEffect(Effects.FIRE_RESISTANCE);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.FIRE_RESISTANCE);
                                    player.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.water_breathing":
                            oldEffect = player.getEffect(Effects.WATER_BREATHING);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.WATER_BREATHING);
                                    player.addEffect(new EffectInstance(Effects.WATER_BREATHING, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.invisibility":
                            oldEffect = player.getEffect(Effects.INVISIBILITY);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.INVISIBILITY);
                                    player.addEffect(new EffectInstance(Effects.INVISIBILITY, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.absorption":
                            oldEffect = player.getEffect(Effects.ABSORPTION);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.ABSORPTION);
                                    player.addEffect(new EffectInstance(Effects.ABSORPTION, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.luck":
                            oldEffect = player.getEffect(Effects.LUCK);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.LUCK);
                                    player.addEffect(new EffectInstance(Effects.LUCK, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.slow_falling":
                            oldEffect = player.getEffect(Effects.SLOW_FALLING);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removeEffect(Effects.SLOW_FALLING);
                                    player.addEffect(new EffectInstance(Effects.SLOW_FALLING, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public static void removeDebuffs(PlayerEvent event) {
        if (event.getPlayer() != null) {
            PlayerEntity player = event.getPlayer();
            if (player.getPersistentData().getBoolean("shouldRemoveSlowness")) {
                player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                player.getPersistentData().putBoolean("shouldRemoveSlowness", false);
            }
            if (player.getPersistentData().getBoolean("shouldRemoveBind")) {
                player.removeEffect(EffectInit.BIND_EFFECT.get());
                player.getPersistentData().putBoolean("shouldRemoveBind", false);
            }
            switch (player.getPersistentData().getString("remove_debuff")) {
                case "":
                    break;
                case "effect.minecraft.poison":
                    player.removeEffect(Effects.POISON);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.chill_effect":
                    player.removeEffect(EffectInit.CHILL_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.bleed_effect":
                    player.removeEffect(EffectInit.BLEED_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.slowness":
                    player.removeEffect(Effects.MOVEMENT_SLOWDOWN);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.bind_effect":
                    player.removeEffect(EffectInit.BIND_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.strength_down_effect":
                    player.removeEffect(EffectInit.STRENGTH_DOWN_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.phys_def_down_effect":
                    player.removeEffect(EffectInit.PHYS_DEF_DOWN_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.wither":
                    player.removeEffect(Effects.WITHER);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.unluck":
                    player.removeEffect(Effects.UNLUCK);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.hunger":
                    player.removeEffect(Effects.HUNGER);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.weakness":
                    player.removeEffect(Effects.WEAKNESS);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.mining_fatigue":
                    player.removeEffect(Effects.DIG_SLOWDOWN);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.nausea":
                    player.removeEffect(Effects.CONFUSION);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.blindness":
                    player.removeEffect(Effects.BLINDNESS);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
            }
            if (player.getHealth() > 1.0) {
                player.getPersistentData().putBoolean("should_die", false);
            }
            if (player.getPersistentData().getInt("poison_time") > 0 && player.getPersistentData().getBoolean("is_poisoned")) {
                int newDuration = player.getPersistentData().getInt("poison_time");
                EffectInstance oldEffect = player.getEffect(Effects.POISON);
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removeEffect(Effects.POISON);
                    player.addEffect(new EffectInstance(Effects.POISON, newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_poisoned", false);
                }
            } else if (player.getPersistentData().getBoolean("is_poisoned")) {
                player.removeEffect(Effects.POISON);
            }
            if (player.getPersistentData().getInt("chill_time") > 0 && player.getPersistentData().getBoolean("is_chilled")) {
                int newDuration = player.getPersistentData().getInt("chill_time");
                EffectInstance oldEffect = player.getEffect(EffectInit.CHILL_EFFECT.get());
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removeEffect(EffectInit.CHILL_EFFECT.get());
                    player.addEffect(new EffectInstance(EffectInit.CHILL_EFFECT.get(), newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_chilled", false);
                }
            } else if (player.getPersistentData().getBoolean("is_chilled")) {
                player.removeEffect(EffectInit.CHILL_EFFECT.get());
            }
            if (player.getPersistentData().getInt("bleed_time") > 0 && player.getPersistentData().getBoolean("is_bleeding")) {
                int newDuration = player.getPersistentData().getInt("bleed_time");
                EffectInstance oldEffect = player.getEffect(EffectInit.BLEED_EFFECT.get());
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removeEffect(EffectInit.BLEED_EFFECT.get());
                    player.addEffect(new EffectInstance(EffectInit.BLEED_EFFECT.get(), newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_bleeding", false);
                }
            }
        }
    }
}
