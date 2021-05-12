package machinamelia.ethergems.common.events;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.MagmaCubeEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.ZombiePigmanEntity;
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

@Mod.EventBusSubscriber(modid = EtherGems.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
                player.addPotionEffect(new EffectInstance(EffectInit.HASTE_EFFECT.get(), 160, (int) fullStrength));
            }
            fullStrength = GemHandler.getPlayerGemStrength(player, "Quick Step");
            if (fullStrength > 25.0) {
                fullStrength = 25.0;
            }
            if (fullStrength > 0) {
                player.addPotionEffect(new EffectInstance(EffectInit.SPEED_EFFECT.get(), 160, (int) fullStrength));
            }
            fullStrength = GemHandler.getPlayerGemStrength(player, "Muscle Up");
            if (fullStrength > 3) {
                fullStrength = 3;
            }
            if (fullStrength > 0) {
                player.addPotionEffect(new EffectInstance(EffectInit.RESISTANCE_EFFECT.get(), 160, (int) (fullStrength / 0.5)));
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
        EffectInstance chillEffect = event.getEntityLiving().getActivePotionEffect(EffectInit.CHILL_EFFECT.get());
        if (chillEffect != null) {
            chillEffect.performEffect(event.getEntityLiving());
        }
        EffectInstance bleedEffect = event.getEntityLiving().getActivePotionEffect(EffectInit.BLEED_EFFECT.get());
        if (bleedEffect != null) {
            bleedEffect.performEffect(event.getEntityLiving());
        }
    }
    @SubscribeEvent
    public static void causeExtraEffectDamage(LivingEvent.LivingUpdateEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entity;
            if (!(livingEntity instanceof BlazeEntity || livingEntity instanceof ZombiePigmanEntity || livingEntity instanceof MagmaCubeEntity || livingEntity instanceof WitherSkeletonEntity)) {
                double fullStrength = entity.getPersistentData().getDouble("blaze_plus_damage");
                if (fullStrength > 0.0 && entityTickCounter <= 0) {
                    if (livingEntity.isBurning()) {
                        livingEntity.attackEntityFrom(DamageSource.ON_FIRE, (float) fullStrength);
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
                if (livingEntity.getActivePotionEffect(EffectInit.CHILL_EFFECT.get()) != null) {
                    livingEntity.attackEntityFrom(DamageSource.MAGIC, (float) fullStrength);
                } else {
                    livingEntity.getPersistentData().putDouble("chill_plus_damage", 0.0);
                }
                entityTickCounter2 = 20;
            } else {
                entityTickCounter2--;
            }
            fullStrength = entity.getPersistentData().getDouble("bleed_plus_damage");
            if (fullStrength > 0.0 && entityTickCounter3 <= 0) {
                if (livingEntity.getActivePotionEffect(EffectInit.BLEED_EFFECT.get()) != null) {
                    livingEntity.attackEntityFrom(DamageSource.MAGIC, (float) fullStrength);
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
            if (event.getPotionEffect().getEffectName().equals("effect.minecraft.slowness") && event.getEntity() instanceof PlayerEntity) {
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
            if (event.getPotionEffect().getEffectName().equals("effect.ethergems.bind_effect")) {
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
            if ((event.getPotionEffect().getEffectName().equals("effect.minecraft.slowness") || event.getPotionEffect().getEffectName().equals("effect.minecraft.wither") || event.getPotionEffect().getEffectName().equals("effect.minecraft.unluck") || event.getPotionEffect().getEffectName().equals("effect.minecraft.hunger") || event.getPotionEffect().getEffectName().equals("effect.minecraft.weakness") || event.getPotionEffect().getEffectName().equals("effect.minecraft.mining_fatigue") || event.getPotionEffect().getEffectName().equals("effect.minecraft.nausea") || event.getPotionEffect().getEffectName().equals("effect.minecraft.blindness") || event.getPotionEffect().getEffectName().equals("effect.ethergems.bind_effect") || event.getPotionEffect().getEffectName().equals("effect.minecraft.poison") || event.getPotionEffect().getEffectName().equals("effect.ethergems.chill_effect") || event.getPotionEffect().getEffectName().equals("effect.ethergems.bleed_effect") || event.getPotionEffect().getEffectName().equals("effect.ethergems.strength_down_effect") || event.getPotionEffect().getEffectName().equals("effect.ethergems.phys_def_down_effect"))) {
                double fullStrength = GemHandler.getPlayerGemStrength(player, "Debuff Resist");
                Random randy = new Random();
                int roll = randy.nextInt(100);
                if (roll < fullStrength) {
                    player.getPersistentData().putString("remove_debuff", event.getPotionEffect().getEffectName());
                } else {
                    player.getPersistentData().putString("remove_debuff", "");
                }
            }
            if (event.getPotionEffect().getEffectName().equals("effect.minecraft.poison")) {
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
            if (event.getPotionEffect().getEffectName().equals("effect.ethergems.chill_effect")) {
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
            if (event.getPotionEffect().getEffectName().equals("effect.ethergems.bleed_effect")) {
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
            if (event.getPotionEffect().getEffectName().equals("effect.minecraft.speed") || event.getPotionEffect().getEffectName().equals("effect.minecraft.haste") || event.getPotionEffect().getEffectName().equals("effect.minecraft.strength") || event.getPotionEffect().getEffectName().equals("effect.minecraft.jump_boost") || event.getPotionEffect().getEffectName().equals("effect.minecraft.regeneration") || event.getPotionEffect().getEffectName().equals("effect.minecraft.resistance") || event.getPotionEffect().getEffectName().equals("effect.minecraft.fire_resistance") || event.getPotionEffect().getEffectName().equals("effect.minecraft.water_breathing") || event.getPotionEffect().getEffectName().equals("effect.minecraft.invisibility") || event.getPotionEffect().getEffectName().equals("effect.minecraft.night_vision") || event.getPotionEffect().getEffectName().equals("effect.minecraft.absorption") || event.getPotionEffect().getEffectName().equals("effect.minecraft.luck") || event.getPotionEffect().getEffectName().equals("effect.minecraft.slow_falling")) {
                if (!player.world.isRemote) {
                    double buffTimePlus = GemHandler.getPlayerGemStrength(player, "Buff Time Plus");
                    if (buffTimePlus > 150.0) {
                        buffTimePlus = 150.0;
                    }
                    if (buffTimePlus > 9 && player.getPersistentData().getString("remove_buff").equals("")) {
                        player.getPersistentData().putString("remove_buff", event.getPotionEffect().getEffectName());
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
            if (!player.world.isRemote) {
                String buff = player.getPersistentData().getString("remove_buff");
                int buffTime = player.getPersistentData().getInt("buff_duration");
                if (buffTime > 0) {
                    switch (buff) {
                        case "effect.minecraft.speed":
                            EffectInstance oldEffect = player.getActivePotionEffect(Effects.SPEED);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.SPEED);
                                    player.addPotionEffect(new EffectInstance(Effects.SPEED, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.haste":
                            oldEffect = player.getActivePotionEffect(Effects.HASTE);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.HASTE);
                                    player.addPotionEffect(new EffectInstance(Effects.HASTE, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.strength":
                            oldEffect = player.getActivePotionEffect(Effects.STRENGTH);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.STRENGTH);
                                    ;
                                    player.addPotionEffect(new EffectInstance(Effects.STRENGTH, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.jump_boost":
                            oldEffect = player.getActivePotionEffect(Effects.JUMP_BOOST);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.JUMP_BOOST);
                                    ;
                                    player.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.regeneration":
                            oldEffect = player.getActivePotionEffect(Effects.REGENERATION);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.REGENERATION);
                                    player.addPotionEffect(new EffectInstance(Effects.REGENERATION, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.resistance":
                            oldEffect = player.getActivePotionEffect(Effects.RESISTANCE);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.RESISTANCE);
                                    player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.fire_resistance":
                            oldEffect = player.getActivePotionEffect(Effects.FIRE_RESISTANCE);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.FIRE_RESISTANCE);
                                    player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.water_breathing":
                            oldEffect = player.getActivePotionEffect(Effects.WATER_BREATHING);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.WATER_BREATHING);
                                    player.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.invisibility":
                            oldEffect = player.getActivePotionEffect(Effects.INVISIBILITY);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.INVISIBILITY);
                                    player.addPotionEffect(new EffectInstance(Effects.INVISIBILITY, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.absorption":
                            oldEffect = player.getActivePotionEffect(Effects.ABSORPTION);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.ABSORPTION);
                                    player.addPotionEffect(new EffectInstance(Effects.ABSORPTION, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.luck":
                            oldEffect = player.getActivePotionEffect(Effects.LUCK);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.LUCK);
                                    player.addPotionEffect(new EffectInstance(Effects.LUCK, buffTime, oldAmplifier));
                                    player.getPersistentData().putString("remove_buff", "");
                                    player.getPersistentData().putInt("buff_duration", 0);
                                    player.getPersistentData().putBoolean("is_new", true);
                                }
                            }
                            break;
                        case "effect.minecraft.slow_falling":
                            oldEffect = player.getActivePotionEffect(Effects.SLOW_FALLING);
                            if (oldEffect != null) {
                                int oldAmplifier = oldEffect.getAmplifier();
                                if (player != null) {
                                    player.removePotionEffect(Effects.SLOW_FALLING);
                                    player.addPotionEffect(new EffectInstance(Effects.SLOW_FALLING, buffTime, oldAmplifier));
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
                player.removePotionEffect(Effects.SLOWNESS);
                player.getPersistentData().putBoolean("shouldRemoveSlowness", false);
            }
            if (player.getPersistentData().getBoolean("shouldRemoveBind")) {
                player.removePotionEffect(EffectInit.BIND_EFFECT.get());
                player.getPersistentData().putBoolean("shouldRemoveBind", false);
            }
            switch (player.getPersistentData().getString("remove_debuff")) {
                case "":
                    break;
                case "effect.minecraft.poison":
                    player.removePotionEffect(Effects.POISON);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.chill_effect":
                    player.removePotionEffect(EffectInit.CHILL_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.bleed_effect":
                    player.removePotionEffect(EffectInit.BLEED_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.slowness":
                    player.removePotionEffect(Effects.SLOWNESS);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.bind_effect":
                    player.removePotionEffect(EffectInit.BIND_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.strength_down_effect":
                    player.removePotionEffect(EffectInit.STRENGTH_DOWN_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.ethergems.phys_def_down_effect":
                    player.removePotionEffect(EffectInit.PHYS_DEF_DOWN_EFFECT.get());
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.wither":
                    player.removePotionEffect(Effects.WITHER);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.unluck":
                    player.removePotionEffect(Effects.UNLUCK);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.hunger":
                    player.removePotionEffect(Effects.HUNGER);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.weakness":
                    player.removePotionEffect(Effects.WEAKNESS);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.mining_fatigue":
                    player.removePotionEffect(Effects.MINING_FATIGUE);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.nausea":
                    player.removePotionEffect(Effects.NAUSEA);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
                case "effect.minecraft.blindness":
                    player.removePotionEffect(Effects.BLINDNESS);
                    player.getPersistentData().putString("remove_debuff", "");
                    break;
            }
            if (player.getHealth() > 1.0) {
                player.getPersistentData().putBoolean("should_die", false);
            }
            if (player.getPersistentData().getInt("poison_time") > 0 && player.getPersistentData().getBoolean("is_poisoned")) {
                int newDuration = player.getPersistentData().getInt("poison_time");
                EffectInstance oldEffect = player.getActivePotionEffect(Effects.POISON);
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removePotionEffect(Effects.POISON);
                    player.addPotionEffect(new EffectInstance(Effects.POISON, newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_poisoned", false);
                }
            } else if (player.getPersistentData().getBoolean("is_poisoned")) {
                player.removePotionEffect(Effects.POISON);
            }
            if (player.getPersistentData().getInt("chill_time") > 0 && player.getPersistentData().getBoolean("is_chilled")) {
                int newDuration = player.getPersistentData().getInt("chill_time");
                EffectInstance oldEffect = player.getActivePotionEffect(EffectInit.CHILL_EFFECT.get());
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removePotionEffect(EffectInit.CHILL_EFFECT.get());
                    player.addPotionEffect(new EffectInstance(EffectInit.CHILL_EFFECT.get(), newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_chilled", false);
                }
            } else if (player.getPersistentData().getBoolean("is_chilled")) {
                player.removePotionEffect(EffectInit.CHILL_EFFECT.get());
            }
            if (player.getPersistentData().getInt("bleed_time") > 0 && player.getPersistentData().getBoolean("is_bleeding")) {
                int newDuration = player.getPersistentData().getInt("bleed_time");
                EffectInstance oldEffect = player.getActivePotionEffect(EffectInit.BLEED_EFFECT.get());
                if (oldEffect != null) {
                    int oldAmplifier = oldEffect.getAmplifier();
                    player.removePotionEffect(EffectInit.BLEED_EFFECT.get());
                    player.addPotionEffect(new EffectInstance(EffectInit.BLEED_EFFECT.get(), newDuration, oldAmplifier));
                    player.getPersistentData().putBoolean("is_bleeding", false);
                }
            }
        }
    }
}
