/*

This class is a remnant from the time Enchanting was perfomed by throwing out the items.
Now, it is performed within GUI entirely

package me.vannername.enchatingplusplus.listeners;

import me.vannername.enchatingplusplus.Main;
import me.vannername.enchatingplusplus.utils.Utils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

import javax.security.auth.login.Configuration;
import java.util.*;

public class ItemLocator implements Listener {
    private Main plugin;

    public ItemLocator(Main pluign) {
        this.plugin = pluign;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    static long cooldown1 = 0, cooldown2 = 0;

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType() == Material.NETHER_STAR) {
            cooldown1 = System.currentTimeMillis();
            if (cooldown1 < cooldown2 + 5000) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Slow down!");
            } else {
                for (Entity n : e.getPlayer().getNearbyEntities(5, 2, 5)) {
                    if (n instanceof Item) {
                        Item item = (Item) n;
                        if (item.getItemStack().getType() == Material.NETHERITE_SWORD) {
                            //e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> sword(e.getPlayer(), item), 7L);
                        } else if (item.getItemStack().getType() == Material.ELYTRA) {
                            e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> elytra(e.getPlayer(), item), 7L);
                        } else if (item.getItemStack().getType() == Material.NETHERITE_HELMET || item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE || item.getItemStack().getType() == Material.NETHERITE_LEGGINGS || item.getItemStack().getType() == Material.NETHERITE_BOOTS) {
                            //e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> armor(e.getPlayer(), item), 7L);
                        } else if (item.getItemStack().getType() == Material.NETHERITE_PICKAXE) {
                            //e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> pickaxe(e.getPlayer(), item), 7L);
                        } else if (item.getItemStack().getType() == Material.BOW && item.getItemStack().containsEnchantment(Enchantment.ARROW_INFINITE)) {
                            //e.getPlayer().getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> bow(e.getPlayer(), item), 7L);
                        }
                    }
                }
                cooldown2 = System.currentTimeMillis();
            }
        }
    }

    public void sword(Player p, Item item) {
        int lvl;
        ItemMeta meta = item.getItemStack().getItemMeta();
        if (!meta.hasLore() || !meta.getLore().get(1).startsWith("0", 8)) {
            Location l = item.getLocation();

            for (Entity m : p.getNearbyEntities(5, 2, 5)) {
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHERITE_SWORD)) m.remove();
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHER_STAR)) {
                    if (((Item) m).getItemStack().getAmount() > 1) {
                        ((Item) m).getItemStack().setAmount(((Item) m).getItemStack().getAmount() - 1);
                    } else m.remove();
                }
            }
            ItemStack res = new ItemStack(Material.NETHERITE_SWORD);
            List<String> lore = new ArrayList();
            if (!meta.hasLore()) {
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(1, "s"), "Level: 1/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(1, "s"));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            } else if (Integer.parseInt(meta.getLore().get(1).substring(7, 8)) + 1 != 10) {
                lvl = Integer.parseInt(meta.getLore().get(1).substring(7, 8)) + 1;
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(lvl, "s"), "Level: " + lvl + "/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(lvl, "s"));
                switch (lvl) {
                    case 2:
                        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                        break;
                    case 3:
                        meta.setCustomModelData(1);
                        break;
                    case 4:
                        meta.setCustomModelData(2);
                        break;
                    case 5:
                        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 2, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                        break;
                    case 6:
                        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", 0.025, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                        break;
                    case 7:
                        meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                        break;
                    case 8:
                        meta.setCustomModelData(3);
                        break;
                    case 9:
                        meta.setCustomModelData(4);
                        break;
                }
            } else {
                Utils.loreAdd(lore, ChatColor.GOLD + "Enchanted to perfection.", "Level: 10/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "All the drops from your enemies are doubled!");
                meta.setCustomModelData(5);
            }
            meta.setLore(lore);


            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            res.setItemMeta(meta);

            p.getWorld().dropItem(l, res);
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 500);
        }
    }

    public void armor(Player p, Item item) {
        int lvl;
        ItemMeta meta = item.getItemStack().getItemMeta();
        if (!meta.hasLore() || !meta.getLore().get(1).startsWith("3", 7)) {
            Location l = item.getLocation();

            for (Entity m : p.getNearbyEntities(5, 2, 5)) {
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHERITE_HELMET || ((Item) m).getItemStack().getType() == Material.NETHERITE_CHESTPLATE || ((Item) m).getItemStack().getType() == Material.NETHERITE_LEGGINGS || ((Item) m).getItemStack().getType() == Material.NETHERITE_BOOTS)) m.remove();
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHER_STAR)) {
                    if (((Item) m).getItemStack().getAmount() > 1) {
                        ((Item) m).getItemStack().setAmount(((Item) m).getItemStack().getAmount() - 1);
                    } else m.remove();
                }
            }
            ItemStack res = new ItemStack(item.getItemStack().getType());
            List<String> lore = new ArrayList();
            if (!meta.hasLore()) {
                //все дальнейшие if`ы для разных типов брони, читать только первый (числа = +2 для защиты брони и +1 для toughness)
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(1, "a"), "Level: 1/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(1, "a"));

                if (item.getItemStack().getType() == Material.NETHERITE_HELMET)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                else if (item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                else if (item.getItemStack().getType() == Material.NETHERITE_LEGGINGS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                else if (item.getItemStack().getType() == Material.NETHERITE_BOOTS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

                if (item.getItemStack().getType() == Material.NETHERITE_HELMET)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                else if (item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                else if (item.getItemStack().getType() == Material.NETHERITE_LEGGINGS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                else if (item.getItemStack().getType() == Material.NETHERITE_BOOTS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

                if (item.getItemStack().getType() == Material.NETHERITE_HELMET)
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                else if (item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE)
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                else if (item.getItemStack().getType() == Material.NETHERITE_LEGGINGS)
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                else if (item.getItemStack().getType() == Material.NETHERITE_BOOTS)
                    meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

            } else if (Integer.parseInt(meta.getLore().get(1).substring(7, 8)) == 1) {
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(2, "a"), "Level: 2/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(2, "a"));

                if (item.getItemStack().getType() == Material.NETHERITE_HELMET)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
                else if (item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
                else if (item.getItemStack().getType() == Material.NETHERITE_LEGGINGS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
                else if (item.getItemStack().getType() == Material.NETHERITE_BOOTS)
                    meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));


            } else {
                Utils.loreAdd(lore, ChatColor.GOLD + "Enchanted to perfection.", "Level: 3/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Special effect!");
                if (item.getItemStack().getType() == Material.NETHERITE_HELMET) meta.setCustomModelData(11);
                else if (item.getItemStack().getType() == Material.NETHERITE_CHESTPLATE) meta.setCustomModelData(12);
                else if (item.getItemStack().getType() == Material.NETHERITE_LEGGINGS) meta.setCustomModelData(13);
                else if (item.getItemStack().getType() == Material.NETHERITE_BOOTS) meta.setCustomModelData(14);
            }


            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            res.setItemMeta(meta);

            p.getWorld().dropItem(l, res);
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 500);
        }
    }

    public void pickaxe(Player p, Item item) {
        int lvl;
        ItemMeta meta = item.getItemStack().getItemMeta();
        if (!meta.hasLore() || !meta.getLore().get(1).startsWith("3", 7)) {
            Location l = item.getLocation();

            for (Entity m : p.getNearbyEntities(5, 2, 5)) {
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHERITE_PICKAXE)) m.remove();
                if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHER_STAR)) {
                    if (((Item) m).getItemStack().getAmount() > 1) {
                        ((Item) m).getItemStack().setAmount(((Item) m).getItemStack().getAmount() - 1);
                    } else m.remove();
                }
            }
            ItemStack res = new ItemStack(Material.NETHERITE_PICKAXE);
            List<String> lore = new ArrayList();
            if (!meta.hasLore()) {
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(1, "p"), "Level: 1/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(1, "p"));
                meta.setCustomModelData(21);
            } else if (Integer.parseInt(meta.getLore().get(1).substring(7, 8)) == 1) {
                Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(2, "p"), "Level: 2/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(2, "p"));
                meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.movement_speed", 0.025, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            } else {
                Utils.loreAdd(lore, ChatColor.GOLD + "Enchanted to perfection.", "Level: 3/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Makes your pickaxe unbreakable. Literally.");
                if (meta.getEnchants().containsKey(Enchantment.MENDING))
                {
                    ItemStack enchbook = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) enchbook.getItemMeta();
                    enchmeta.addStoredEnchant(Enchantment.MENDING, 1, true);
                    enchbook.setItemMeta(enchmeta);
                    meta.removeEnchant(Enchantment.MENDING);
                    p.getWorld().dropItem(l, enchbook);
                }
                if (meta.getEnchants().containsKey(Enchantment.DURABILITY))
                {
                    ItemStack enchbook = new ItemStack(Material.ENCHANTED_BOOK);
                    EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) enchbook.getItemMeta();
                    enchmeta.addStoredEnchant(Enchantment.DURABILITY, meta.getEnchantLevel(Enchantment.DURABILITY), true);
                    enchbook.setItemMeta(enchmeta);
                    meta.removeEnchant(Enchantment.DURABILITY);
                    p.getWorld().dropItem(l, enchbook);
                }
                meta.setUnbreakable(true);
            }


            meta.setLore(lore);

            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            res.setItemMeta(meta);

            p.getWorld().dropItem(l, res);
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 500);
        }
    }

    public void elytra(Player p, Item item) {
        boolean forward = false;
        ItemMeta meta = item.getItemStack().getItemMeta();
        ItemMeta meta2 = null;
        if (item.getItemStack().getType() == Material.ELYTRA) {
            Location l = item.getLocation();

            for (Entity m : p.getNearbyEntities(5, 2, 5)) {
                if (m instanceof Item) {
                    if (((Item) m).getItemStack().hasItemMeta()) {
                        if (((Item) m).getItemStack().getItemMeta().hasCustomModelData()) {
                            if (((Item) m).getItemStack().getItemMeta().getCustomModelData() == 12) {
                                forward = true;
                            }
                        }
                    }
                }
            }
            if (!forward) return;
            for (Entity m : p.getNearbyEntities(5, 2, 5)) {
                if (m instanceof Item) {
                    if (((Item) m).getItemStack().getType() == Material.NETHERITE_CHESTPLATE)
                        meta2 = ((Item) m).getItemStack().getItemMeta();
                    if ((((Item) m).getItemStack().getType() == Material.NETHERITE_CHESTPLATE || ((Item) m).getItemStack().getType() == Material.ELYTRA))
                        m.remove();
                    if ((((Item) m).getItemStack().getType() == Material.NETHER_STAR)) {
                        if (((Item) m).getItemStack().getAmount() > 1) {
                            ((Item) m).getItemStack().setAmount(((Item) m).getItemStack().getAmount() - 1);
                        } else m.remove();
                    }
                }

            }
            ItemStack res = new ItemStack(Material.ELYTRA);
            List<String> lore = new ArrayList();

            Utils.loreAdd(lore, ChatColor.GOLD + "When pigs fly...", "Level: FULL", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Your chestplate and elytras are now one and the same.");
            meta2.setLore(lore);

            res.setItemMeta(meta2);
            res.addEnchantments(meta.getEnchants());

            p.getWorld().dropItem(l, res);
            p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 500);
        }
    }

    public void bow(Player p, Item item) {
        ItemMeta meta = item.getItemStack().getItemMeta();
        boolean one_or_two = meta.hasLore();
        Location l = item.getLocation();

        for (Entity m : p.getNearbyEntities(5, 2, 5)) {
            if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.BOW)) m.remove();
            if (m instanceof Item && (((Item) m).getItemStack().getType() == Material.NETHER_STAR)) {
                if (((Item) m).getItemStack().getAmount() > 1) {
                    ((Item) m).getItemStack().setAmount(((Item) m).getItemStack().getAmount() - 1);
                } else m.remove();
            }
        }
        ItemStack res = new ItemStack(Material.BOW);
        List<String> lore = new ArrayList();
        if (!one_or_two) {
            Utils.loreAdd(lore, ChatColor.GOLD + "Now truly infinite, without any limitations", "Level: 1/2", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Upgrades your \"Infinity\" enchantment to \"True Infinity\" -");
            Utils.loreAdd(lore, ChatColor.AQUA + "now you don`t waste any arrows of any type");
            meta.setCustomModelData(31);
        } else {
            Utils.loreAdd(lore, ChatColor.GOLD + "Mended to become even better.", "Level: 2/2", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Now has \"Mending\" enchantment regardless of other enchantments");
            meta.addEnchant(Enchantment.MENDING, 1, true);
        }
        meta.setLore(lore);
        res.setItemMeta(meta);

        p.getWorld().dropItem(l, res);
        p.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, l, 500);
    }
}*/
