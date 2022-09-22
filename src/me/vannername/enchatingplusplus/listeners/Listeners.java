package me.vannername.enchatingplusplus.listeners;

import me.vannername.enchatingplusplus.Main;
import me.vannername.enchatingplusplus.utils.PlayerPassive;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

public class Listeners implements Listener {
    private Main plugin;

    public Listeners(Main plugin) {
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    static ArrayList<Material> forbiddenMaterials = new ArrayList<>(){};
//    static ArrayList<Material> forbiddenEntities = new ArrayList<>(){};

    static ArrayList<PotionEffectType> forbiddenEff = new ArrayList<>();

    static String[] part1 = {"NETHERITE", "DIAMOND", "IRON", "GOLD", "COAL", "EMERALD", "COPPER", "REDSTONE", "LAPIS"};
    static String[] part2 = {"_BLOCK", "_ORE"};

    public static void loadForbidden() {

        for (String s : part1) {
            for (String value : part2) {
                Material buffer = Material.getMaterial(s + value);
                if (buffer != null) forbiddenMaterials.add(buffer);
            }
        }

        for(Material m : Material.values()) {
            if(m.getMaxStackSize() == 1) forbiddenMaterials.add(m);
        }

        for(Material m : forbiddenMaterials.toArray(new Material[]{})) {
            if(m.toString().contains("ORE") && !m.toString().contains("DEEPSLATE")) {
                Material buffer = Material.getMaterial("DEEPSLATE_" + m);
                if (buffer != null) forbiddenMaterials.add(buffer);
            }
        }

        forbiddenMaterials.remove(Material.EMERALD);
        forbiddenMaterials.remove(Material.DIAMOND);

        forbiddenMaterials.add(Material.LAPIS_LAZULI);
        forbiddenMaterials.add(Material.NETHERITE_INGOT);
//        forbiddenMaterials.add(Material.GOLD_BLOCK);
//        forbiddenMaterials.add(Material.GOLD_ORE);
        forbiddenMaterials.add(Material.NETHER_GOLD_ORE);
        forbiddenMaterials.add(Material.NETHER_QUARTZ_ORE);

        forbiddenMaterials.add(Material.ICE);
        forbiddenMaterials.add(Material.PACKED_ICE);
        forbiddenMaterials.add(Material.BLUE_ICE);
        forbiddenMaterials.add(Material.CHEST);

        forbiddenMaterials.add(Material.GOLDEN_APPLE);
        forbiddenMaterials.add(Material.ENCHANTED_GOLDEN_APPLE);
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
//            c.getWorld().dropItem(p.getLocation(), new ItemStack(m));
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

    class Damages {
        static Map<Player, HashMap<UUID, Double>> damages = new HashMap<>();

        static void addDamage(Player damager, Entity e, double damage) {
            if (!damages.containsKey(damager))
                damages.put(damager, new HashMap<>());

            HashMap<UUID, Double> temp = damages.get(damager);
            if(!temp.containsKey(e.getUniqueId()))
                temp.put(e.getUniqueId(), 0D);

            temp.put(e.getUniqueId(), temp.get(e.getUniqueId()) + damage);
        }
        static int getDamage(Player p, Entity e) {
            return damages.get(p).remove(e.getUniqueId()).intValue();
        }

        static int getRegenDuration(Player p, Entity e) {
            int base = Damages.getDamage(p, e) / 10;
            if(base == 0) base = 1;
            if(base > 15) base = 15;
            return base*20;
        }
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        try {
            if (isLegalEntity(e.getEntity())) {
                if (e.getDamager() instanceof Player damager && Objects.requireNonNull((damager).getInventory().getItemInMainHand().getItemMeta()).hasCustomModelData()) {
                    if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
                    if(e.getEntity() instanceof Player damaged && (new PlayerPassive(damaged).isPassive() || new PlayerPassive(damager).isPassive())) {
                    } else {
                        int cmd = damager.getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
                        try {
                            LivingEntity entity = (LivingEntity) e.getEntity();
                            if (cmd < 10) {
                                if (cmd >= 4) {
                                    Damages.addDamage(damager, e.getEntity(), e.getDamage());
                                }
                                if (cmd >= 3) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 100, 0, false, false, false));
                                }
                                if (cmd >= 2) {
                                    entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 0, false, false, false));
                                }
                            }
                        } catch (ClassCastException ignored) {}
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private static boolean isLegalEntity(Entity e) {
        return !(e instanceof Player) &&
                !(e instanceof Villager) &&
                !(e instanceof ArmorStand) &&
                !(e instanceof ItemFrame);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        for (Entity n : e.getEntity().getNearbyEntities(10, 4, 10)) {
            if (n instanceof Player p && isLegalEntity(e.getEntity())) {
                ItemStack i = p.getInventory().getItemInMainHand();
//                if (((Player) n).getInventory().getItemInMainHand().getType() != Material.NETHERITE_SWORD)
//                    i = ((Player) n).getInventory().getItemInOffHand();

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
                                if (!(e.getEntity() instanceof Enderman && Objects.requireNonNull(((Enderman) e.getEntity()).getCarriedBlock()).getMaterial() == is.getType())) // prevent the doubling of the blocks Enderman is holding
                                    if (!forbiddenMaterials.contains(is.getType())) is.setAmount(is.getAmount() * 2);
                            }
                        }

                        if(i.getItemMeta().getCustomModelData() >= 4)
                            p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Damages.getRegenDuration(p, e.getEntity()), 0, false, true, false));

                    }
                }
                return;
            }
        }
    }


    /*IMPORTANT NOTE!
    * STACKED EFFECT > BEACON > ACHIEVEMENT EFFECT'S DURATIONS > ADDED EFFECTS!
    * */

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

    @EventHandler
    public void onSkullPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            if (e.getItem().getItemStack().getType() == Material.WITHER_SKELETON_SKULL) {
                e.setCancelled(true);
            }
        }
    }

    private static HashMap<Player, Integer> pick_ids = new HashMap<>();

    private void cancelTask(Player p) {
        if (pick_ids.get(p) != null)
            Bukkit.getScheduler().cancelTask(pick_ids.get(p));
    }

    @EventHandler
    public void onMainHandChange(PlayerItemHeldEvent e) {
        cancelTask(e.getPlayer()); // haste repetition cancelled by default to prevent task stacking

        try {
            // getNewSlot() is used bc getItemInMainHand() is updated AFTER this method runs
            int cmd1 = e.getPlayer().getInventory().getItem(e.getNewSlot()).getItemMeta().getCustomModelData();
            if (cmd1 == 21) { // 1+ lvl pick only

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    try {
                        int cmd2 = e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getCustomModelData();
                        if (cmd1 == cmd2) { // check if player's still holding 1+ lvl pick

                            pick_ids.put(e.getPlayer(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin,
                                    () -> e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 100, 0, true, true, true)),
                                    0L, 60L));
                        }
                    } catch (NullPointerException | IllegalStateException ignored) {}

                }, 10L);

            }
        } catch (NullPointerException | IllegalStateException ignored) {}
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