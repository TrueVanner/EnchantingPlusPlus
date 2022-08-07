package me.vannername.enchatingplusplus.listeners;

import me.vannername.enchatingplusplus.Main;
import me.vannername.enchatingplusplus.utils.PlayerPassive;
import me.vannername.enchatingplusplus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Beacon;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.*;

public class Listeners implements Listener {
    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    static ArrayList<Material> forbiddenMaterials = new ArrayList<>(){};
    static ArrayList<Material> forbiddenEntities = new ArrayList<>(){};

    static ArrayList<PotionEffectType> forbiddenEff = new ArrayList<>();

    static String[] part1 = {"WOODEN", "STONE", "NETHERITE", "DIAMOND", "IRON", "GOLDEN", "CHAINMAIL", "LEATHER"};
    static String[] part2 = {"HELMET", "CHESTPLATE", "LEGGINGS", "BOOTS", "SWORD", "PICKAXE", "AXE", "SHOVEL", "HOE", "INGOT", "BLOCK", "ORE"};

    static String[] part3 = {"COAL", "EMERALD", "COPPER", "REDSTONE", "LAPIS"};
    static String[] part4 = {"", "_BLOCK", "_ORE"};

    public static void loadForbidden() {

        for (String item : part1) {
            for (String s : part2) {
                Material buffer = Material.getMaterial(item + "_" + s);
                if (buffer != null) forbiddenMaterials.add(buffer);
            }
        }

        for (String s : part3) {
            for (String value : part4) {
                Material buffer = Material.getMaterial(s + value);
                if (buffer != null) forbiddenMaterials.add(buffer);
            }
        }

        forbiddenMaterials.remove(Material.IRON_INGOT);
        forbiddenMaterials.remove(Material.EMERALD);

        forbiddenMaterials.add(Material.LAPIS_LAZULI);
        //forbiddenMaterials.add(Material.GOLD_INGOT);
        forbiddenMaterials.add(Material.GOLD_BLOCK);
        forbiddenMaterials.add(Material.GOLD_ORE);
        //forbiddenMaterials.add(Material.COPPER_INGOT);
        forbiddenMaterials.add(Material.NETHER_GOLD_ORE);
        forbiddenMaterials.add(Material.NETHER_QUARTZ_ORE);

        forbiddenMaterials.add(Material.ICE);
        forbiddenMaterials.add(Material.PACKED_ICE);
        forbiddenMaterials.add(Material.BLUE_ICE);
        forbiddenMaterials.add(Material.CHEST);
        forbiddenMaterials.add(Material.BOW);
        forbiddenMaterials.add(Material.CROSSBOW);

        forbiddenMaterials.add(Material.DEEPSLATE_COAL_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_COPPER_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_IRON_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_EMERALD_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_GOLD_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_LAPIS_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_REDSTONE_ORE);
        forbiddenMaterials.add(Material.DEEPSLATE_DIAMOND_ORE);

        forbiddenMaterials.add(Material.SHULKER_BOX);
        forbiddenMaterials.add(Material.POTION);
        forbiddenMaterials.add(Material.SPLASH_POTION);
        forbiddenMaterials.add(Material.LINGERING_POTION);
        //forbiddenMaterials.add(Material.SHULKER_SHELL);
        forbiddenMaterials.add(Material.GOLDEN_APPLE);
        forbiddenMaterials.add(Material.ENCHANTED_GOLDEN_APPLE);
        forbiddenMaterials.add(Material.TOTEM_OF_UNDYING);
        forbiddenMaterials.add(Material.ELYTRA);
        forbiddenMaterials.add(Material.PLAYER_HEAD);
        forbiddenMaterials.add(Material.BEACON);
        forbiddenMaterials.add(Material.NETHER_STAR);
        forbiddenMaterials.add(Material.ANCIENT_DEBRIS);
        forbiddenMaterials.add(Material.HEART_OF_THE_SEA);
        forbiddenMaterials.add(Material.NETHERITE_SCRAP);
        //--------------------------------------//
        forbiddenEff.add(PotionEffectType.ABSORPTION);
        forbiddenEff.add(PotionEffectType.SATURATION);
        forbiddenEff.add(PotionEffectType.REGENERATION);
    }

    public static void testForbidden(ConsoleCommandSender c) {
        for (Material m : forbiddenMaterials) {
            c.sendMessage(m.toString());
//            p.getWorld().dropItem(p.getLocation(), new ItemStack(m));
        }
    }

    @EventHandler
    public void OnItemDrop(PlayerDropItemEvent e) {
        ItemStack i = e.getItemDrop().getItemStack();
        if (i.getType() == Material.NETHERITE_SWORD) {
            if (i.hasItemMeta()) {
                ItemMeta meta = i.getItemMeta();
                if (meta.hasCustomModelData()) {
                    e.setCancelled(true);
                    List<String> lore = meta.getLore();

                    if (meta.getDisplayName().equals("")) meta.setDisplayName("Netherite Sword");

                    String itemName = ChatColor.ITALIC + meta.getDisplayName();

                    if (lore.get(4).contains("on")) {
                        lore.set(4, ChatColor.RED + "Magnetizm is now off!");
                        meta.setDisplayName(ChatColor.RED + itemName);
                    } else {
                        lore.set(4, ChatColor.GREEN + "Magnetizm is now on!");
                        meta.setDisplayName(ChatColor.GREEN + itemName);
                    }

                    meta.setLore(lore);
                    i.setItemMeta(meta);

                    e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        meta.setDisplayName(ChatColor.ITALIC + ChatColor.stripColor(itemName));
                        e.getPlayer().getInventory().getItem(e.getPlayer().getInventory().first(i.getType())).setItemMeta(meta);
                    }, 7L);
                }
            }
        }
    }

    @EventHandler
    public void onShooting(EntityShootBowEvent e) {
        if (e.getBow().hasItemMeta() && e.getBow().getItemMeta().hasCustomModelData() && e.getBow().getItemMeta().getCustomModelData() == 31) {
            e.setConsumeItem(false);
            ((Player) e.getEntity()).updateInventory();
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        try {
            if (!(e.getEntity() instanceof ArmorStand)) {
                if (e.getDamager() instanceof Player damager && Objects.requireNonNull((damager).getInventory().getItemInMainHand().getItemMeta()).hasCustomModelData()) {
                    if(e.getEntity() instanceof Player damaged && (new PlayerPassive(damaged).isPassive() || new PlayerPassive(damager).isPassive())) {
                    } else {
                        int cmd = damager.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
                        try {
                            LivingEntity entity = (LivingEntity) e.getEntity();
                            if (cmd < 10) {
                                if (cmd >= 4) {
                                    damager.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 0, false, false, false));
                                }
                                if (cmd >= 3) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0, false, false, false));
                                }
                                if (cmd >= 2) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 0, false, false, false));
                                }
                            }
                        } catch (ClassCastException ignored) {}
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        for (Entity n : e.getEntity().getNearbyEntities(10, 4, 10)) {
            if (n instanceof Player &&
                    !(e.getEntity() instanceof Player) &&
                    !(e.getEntity() instanceof Villager) &&
                    !(e.getEntity() instanceof ArmorStand) &&
                    !(e.getEntity() instanceof ItemFrame)) {
                ItemStack i = ((Player) n).getInventory().getItemInMainHand();
                if (((Player) n).getInventory().getItemInMainHand().getType() != Material.NETHERITE_SWORD)
                    i = ((Player) n).getInventory().getItemInOffHand();

                if (i.getType() == Material.NETHERITE_SWORD) {

                if (Objects.requireNonNull(i.getItemMeta()).hasCustomModelData()) {
                        if (i.getItemMeta().getLore().get(4).contains("on")) {

                            n.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                                for (Entity m : n.getNearbyEntities(15, 6, 15)) {
                                    if ((m instanceof Item || m instanceof ExperienceOrb) && m.getTicksLived() < 4) {
                                        if (m instanceof Item) ((Item) m).setPickupDelay(0);
                                        m.teleport(n);
                                    }
                                }
                            }, 1L);
                        }
                        if (i.getItemMeta().getCustomModelData() == 5) {
                            for (ItemStack is : e.getDrops()) {
                                if (!(e.getEntity() instanceof Enderman && ((Enderman) e.getEntity()).getCarriedBlock().getMaterial() == is.getType())) // prevent the doubling of the blocks Enderman is holding
                                    if (!forbiddenMaterials.contains(is.getType())) is.setAmount(is.getAmount() * 2);
                            }
                        }
                    }
                }
                return;
            }
        }
    }


    /*IMPORTANT NOTE!
    * STACKED EFFECT > BEACON > ACHIEVEMENT EFFECT'S DURATIONS > ADDED EFFECTS!
    * */
    @EventHandler
    public void onSkullPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if (e.getItem().getItemStack().getType() == Material.WITHER_SKELETON_SKULL) {
                e.setCancelled(true);
            }
        }
    }


    PotionEffect pt1, pt2;


    @EventHandler
    public void effectStacking(EntityPotionEffectEvent e) {
        try {
            if (e.getOldEffect() != null && e.getNewEffect() != null) {
                pt1 = e.getOldEffect();
                pt2 = e.getNewEffect();
                if (pt1.getType() == pt2.getType() && pt1.getDuration() > pt2.getDuration()) {
                    if (!forbiddenEff.contains(pt1.getType())) {
                        if (pt1.getAmplifier() == 0) {
                            e.setCancelled(true);
                            ((Player) e.getEntity()).playSound(e.getEntity().getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 25, 1);
                            ((Player) e.getEntity()).addPotionEffect(new PotionEffect(pt2.getType(), 500, 1, true, true, true));
                        } else if (pt1.getAmplifier() == 1 && e.getCause() == EntityPotionEffectEvent.Cause.BEACON) {
                            ((Player) e.getEntity()).addPotionEffect(new PotionEffect(pt2.getType(), 500, 1, true, true, true));
                        }
                    }
                }
            }
        } catch (NullPointerException ignored) {}
    }

    private static HashMap<Player, Integer> pick_ids = new HashMap<>();

    @EventHandler
    public void onMainHandChange(PlayerItemHeldEvent e) {
        ItemStack i = e.getPlayer().getInventory().getItem(e.getNewSlot());

        try {
            if (i.getItemMeta().hasCustomModelData() && i.getItemMeta().getCustomModelData() == 21) // only lvl 1+ pickaxe
                pick_ids.put(e.getPlayer(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                    () -> e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 0, true, true, true)),
                    10L, 60L));
            else {
                if(pick_ids.get(e.getPlayer()) != null)
                    Bukkit.getScheduler().cancelTask(pick_ids.get(e.getPlayer()));
            }
        } catch (NullPointerException ex) {
            if(pick_ids.get(e.getPlayer()) != null)
                Bukkit.getScheduler().cancelTask(pick_ids.get(e.getPlayer()));
        }
    }

    //    @EventHandler
//    public void onInventoryOpen(InventoryInteractEvent e) {
//
//        //e.getPlayer().sendMessage("gets here");
//        e.getWhoClicked().sendMessage("gets here");
//
//        HashMap<Integer, ? extends ItemStack> all = e.getInventory().all(Material.NETHERITE_SWORD);           actually, it works and is really cool, i just don`t need it lol
//        ItemStack[] swords = all.values().toArray(new ItemStack[]{});
//        Integer[] slots = all.keySet().toArray(new Integer[]{});
//        for (int n = 0; n < swords.length; n++) {
//            ItemStack i = swords[n];
//            if (i.hasItemMeta() && i.getItemMeta().hasLore() && i.getItemMeta().getLore().size() >= 4) {
//                ItemMeta meta = i.getItemMeta();
//                meta.setDisplayName(ChatColor.RESET + ChatColor.stripColor(meta.getDisplayName()));
//                i.setItemMeta(meta);
//            }
//            e.getInventory().setItem(slots[n], i);
//        }
//    }


    //what lies here must not be released into the world ever again...
/*
    @EventHandler
    public void onShoot1(ProjectileLaunchEvent e) {
        if(e.getEntity() instanceof Arrow) {
            Arrow arrow = (Arrow) e.getEntity();

            Vector v = e.getEntity().getVelocity();
            Class c = Arrow.class;

            if(arrow.getShooter() instanceof Player) {
                Player shooter = (Player) arrow.getShooter();
                if(shooter.getInventory().getItemInMainHand().getType() == Material.BOW) {
                    try {
                        int cmd = shooter.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
                        if (cmd >= 33) {
                            c = SpectralArrow.class;
                        }
                        if (cmd >= 32) {
                            v.multiply(2);
                        }
                        e.setCancelled(true);
                        shooter.launchProjectile(c).setVelocity(v);
                    } catch (NullPointerException ignored) {}
                }
            }
        }
    }

    @EventHandler
    public void onShoot2(EntityShootBowEvent e) {
        if (e.getProjectile() instanceof AbstractArrow) {
            if (e.getEntity() instanceof Player) {
                try {
                    int cmd = ((Player) e.getEntity()).getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
                    if (cmd == 31) {
                        ((AbstractArrow) e.getProjectile()).setDamage(((AbstractArrow) e.getProjectile()).getDamage() + 2);
                    }
                } catch (NullPointerException ignored) {}
            }
        }
    }
    */
}