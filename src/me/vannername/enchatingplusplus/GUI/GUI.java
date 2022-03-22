package me.vannername.enchatingplusplus.GUI;

import me.vannername.enchatingplusplus.Main;
import me.vannername.enchatingplusplus.utils.Utils;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class GUI {

    public static Inventory inv;
    public static String inv_name;
    public static int inv_rows = 3 * 9;


    public static void init() {
        inv_name = ChatColor.AQUA + "Enchanting GUI";
        inv = Bukkit.createInventory(null, inv_rows);
    }

    public static ItemStack sword(ItemStack item) {
        int lvl = 1;
        ItemMeta meta = item.getItemMeta();
        ItemStack res = new ItemStack(Material.NETHERITE_SWORD);

        if (meta.hasLore() && meta.getLore().get(1).startsWith("0", 8)) return null;

        List<String> lore = new ArrayList();
        if (!meta.hasLore()) {
            Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(1, "s"), "Level: 1/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(1, "s"));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, new AttributeModifier(UUID.randomUUID(), "generic.attack_damage", 9, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", -2.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
        } else if (!meta.getLore().get(1).startsWith("9", 7)) {
            lvl = Integer.parseInt(meta.getLore().get(1).substring(7, 8)) + 1;
            Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(lvl, "s"), "Level: " + lvl + "/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(lvl, "s"));
            switch (lvl) {
                case 2:
                    meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(UUID.randomUUID(), "generic.attack_speed", 0.4, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HAND));
                    break;
                case 3:
                    meta.setCustomModelData(1);
                    Utils.loreAdd(lore, ChatColor.AQUA+"Press the \"Throw Item\" key to toggle magnetizm on and off.");
                    Utils.loreAdd(lore,ChatColor.GREEN+"Magnetizm in now on!");
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
            lvl = 10;
            Utils.loreAdd(lore, ChatColor.GOLD + "Enchanted to perfection.", "Level: 10/10", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "All the drops from your enemies are doubled!");
            meta.setCustomModelData(5);
        }
        if (lvl > 3) {
            Utils.loreAdd(lore, "");
            Utils.loreAdd(lore,ChatColor.GREEN+"Magnetizm in now on!");
        }
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        res.setItemMeta(meta);
        return res;
    }

    public static ItemStack armor(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore() && meta.getLore().get(1).startsWith("3", 7)) return null;
        ItemStack res = new ItemStack(item.getType());
        List<String> lore = new ArrayList();
        if (!meta.hasLore()) {
            //все дальнейшие if`ы для разных типов брони, читать только первый (числа = +2 для защиты брони и +1 для toughness)
            Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(1, "a"), "Level: 1/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(1, "a"));

            if (item.getType() == Material.NETHERITE_HELMET)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            else if (item.getType() == Material.NETHERITE_CHESTPLATE)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 10, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            else if (item.getType() == Material.NETHERITE_LEGGINGS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 8, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            else if (item.getType() == Material.NETHERITE_BOOTS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR, new AttributeModifier(UUID.randomUUID(), "generic.armor", 5, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

            if (item.getType() == Material.NETHERITE_HELMET)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            else if (item.getType() == Material.NETHERITE_CHESTPLATE)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            else if (item.getType() == Material.NETHERITE_LEGGINGS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            else if (item.getType() == Material.NETHERITE_BOOTS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 3, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

            if (item.getType() == Material.NETHERITE_HELMET)
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            else if (item.getType() == Material.NETHERITE_CHESTPLATE)
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            else if (item.getType() == Material.NETHERITE_LEGGINGS)
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            else if (item.getType() == Material.NETHERITE_BOOTS)
                meta.addAttributeModifier(Attribute.GENERIC_KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "minecraft:generic.knockback_resistance", 0.1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));

        } else if (Integer.parseInt(meta.getLore().get(1).substring(7, 8)) == 1) {
            Utils.loreAdd(lore, ChatColor.GOLD + Utils.enchants_text(2, "a"), "Level: 2/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + Utils.enchants_stats(2, "a"));

            if (item.getType() == Material.NETHERITE_HELMET)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.HEAD));
            else if (item.getType() == Material.NETHERITE_CHESTPLATE)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.CHEST));
            else if (item.getType() == Material.NETHERITE_LEGGINGS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.LEGS));
            else if (item.getType() == Material.NETHERITE_BOOTS)
                meta.addAttributeModifier(Attribute.GENERIC_ARMOR_TOUGHNESS, new AttributeModifier(UUID.randomUUID(), "generic.armor_toughness", 1, AttributeModifier.Operation.ADD_NUMBER, EquipmentSlot.FEET));
        } else {
            Utils.loreAdd(lore, ChatColor.GOLD + "Enchanted to perfection.", "Level: 3/3", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "OVERPROTECTION!");
//            if (item.getType() == Material.NETHERITE_HELMET) meta.setCustomModelData(11);
//            else if (item.getType() == Material.NETHERITE_CHESTPLATE) meta.setCustomModelData(12);
//            else if (item.getType() == Material.NETHERITE_LEGGINGS) meta.setCustomModelData(13);
//            else if (item.getType() == Material.NETHERITE_BOOTS) meta.setCustomModelData(14);
            if (item.getType() == Material.NETHERITE_HELMET) meta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 4, true);
            else if (item.getType() == Material.NETHERITE_CHESTPLATE) meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
            else if (item.getType() == Material.NETHERITE_LEGGINGS) meta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 4, true);
            else if (item.getType() == Material.NETHERITE_BOOTS) meta.addEnchant(Enchantment.PROTECTION_FIRE, 4, true);
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        res.setItemMeta(meta);
        return res;
    }

    public static ItemStack pickaxe(ItemStack item, Player p) {
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore() && meta.getLore().get(1).startsWith("3", 7)) return null;

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
            if (meta.getEnchants().containsKey(Enchantment.MENDING)) {
                ItemStack enchbook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) enchbook.getItemMeta();
                enchmeta.addStoredEnchant(Enchantment.MENDING, 1, true);
                enchbook.setItemMeta(enchmeta);
                meta.removeEnchant(Enchantment.MENDING);
                p.getWorld().dropItem(p.getLocation(), enchbook);
            }
            if (meta.getEnchants().containsKey(Enchantment.DURABILITY)) {
                ItemStack enchbook = new ItemStack(Material.ENCHANTED_BOOK);
                EnchantmentStorageMeta enchmeta = (EnchantmentStorageMeta) enchbook.getItemMeta();
                enchmeta.addStoredEnchant(Enchantment.DURABILITY, meta.getEnchantLevel(Enchantment.DURABILITY), true);
                enchbook.setItemMeta(enchmeta);
                meta.removeEnchant(Enchantment.DURABILITY);
                p.getWorld().dropItem(p.getLocation(), enchbook);
            }
            meta.setUnbreakable(true);
        }

        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        res.setItemMeta(meta);
        return res;
    }

    public static ItemStack bow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if ((meta.hasLore() && meta.getLore().get(1).startsWith("2", 7)) || !meta.hasEnchant(Enchantment.ARROW_INFINITE)) return null;

        ItemStack res = new ItemStack(Material.BOW);
        List<String> lore = new ArrayList();
        if (!meta.hasLore()) {
            Utils.loreAdd(lore, ChatColor.GOLD + "Now truly infinite, without any limitations", "Level: 1/2", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Upgrades your \"Infinity\" enchantment to \"True Infinity\" -");
            Utils.loreAdd(lore, ChatColor.AQUA + "now you don`t waste any arrows of any type");
            meta.setCustomModelData(31);
        } else {
            Utils.loreAdd(lore, ChatColor.GOLD + "Mended to become even better.", "Level: 2/2", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Now has \"Mending\" enchantment regardless of other enchantments");
            meta.addEnchant(Enchantment.MENDING, 1, true);
        }
        meta.setLore(lore);
        res.setItemMeta(meta);
        return res;
    }

//    public static ItemStack trident(Item) {
//
//    }

    public static boolean craft(Inventory inv, Player p, int mode) {
        ItemStack res = null;
        //p.sendMessage(String.valueOf(mode));
        switch (mode) {
            case 0:
                res = sword(inv.getItem(10));
                break;
            case 1:
            case 2:
            case 3:
            case 4:
                res = armor(inv.getItem(10));
                break;
            case 5:
                res = pickaxe(inv.getItem(10), p);
                break;
            case 6:
                res = bow(inv.getItem(10));
                break;
            case 7:
                res = inv.getItem(9);
                ItemMeta meta = inv.getItem(10).getItemMeta();
                ItemMeta meta2 = res.getItemMeta();

                List<String> lore = new ArrayList();
                Utils.loreAdd(lore, ChatColor.GOLD + "When pigs fly...", "Level: FULL", ChatColor.BLUE + "Current bonus: " + ChatColor.AQUA + "Your chestplate and elytras are now one and the same.");
                meta.setLore(lore);

                res.setItemMeta(meta);
                res.addEnchantments(meta2.getEnchants());
                break;
        }
        if (res == null) return false;

        inv.setItem(10, new ItemStack(Material.AIR));
        inv.setItem(13, new ItemStack(Material.AIR));
        Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 0, 3);
        Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 3, 3);
        inv.setItem(16, res);
        p.getServer().getLogger().log(new LogRecord(Level.SEVERE, p.getName() + " Enchanted " + res.getType().name() + ", lvl: " + res.getItemMeta().getLore().get(1).substring(7, 8)));
        p.playSound(p.getLocation(), Sound.BLOCK_SMITHING_TABLE_USE, 100, 1);
        return true;
    }

    public static Inventory GUI(Player p) {
        Inventory toReturn = Bukkit.createInventory(null, inv_rows, inv_name);
        boolean b1 = false, b2 = false;
        ArrayList<Material> correct = new ArrayList<Material>(){};
        correct.add(Material.NETHERITE_SWORD);
        correct.add(Material.NETHERITE_HELMET);
        correct.add(Material.NETHERITE_CHESTPLATE);
        correct.add(Material.NETHERITE_LEGGINGS);
        correct.add(Material.NETHERITE_BOOTS);
        correct.add(Material.NETHERITE_PICKAXE);
        correct.add(Material.BOW);

        try {if (correct.contains(inv.getItem(10).getType())) b1 = true;} catch (NullPointerException ignored) {}

        if (b1) {
            if (inv.getItem(9) != null && inv.getItem(9).getType() != Material.ELYTRA)
                Utils.createItemSquare(inv, Material.GREEN_STAINED_GLASS_PANE, 0, 3);
        } else
            Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 0, 3);

        if (inv.getItem(13) != null) {
            if (inv.getItem(13).getType() == Material.NETHER_STAR) {
                Utils.createItemSquare(inv, Material.GREEN_STAINED_GLASS_PANE, 3, 3);
                b2 = true;
                //если предмет - элитры, и если в 10 слоту максимальный нагрудник
            } else if (inv.getItem(13).getType() == Material.ELYTRA && (inv.getItem(10).getType() == Material.NETHERITE_CHESTPLATE && inv.getItem(10).containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) && inv.getItem(10).containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS))) {
                Utils.createItemSquare(inv, Material.GREEN_STAINED_GLASS_PANE, 0, 3);
                inv.setItem(9, inv.getItem(13));
                inv.setItem(13, new ItemStack(Material.AIR));
            } else Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 3, 3);
        } else
            Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 3, 3);
        if (b1 && b2) {
            if (inv.getItem(9).getType() != Material.ELYTRA) {
                if (craft(inv, p, correct.indexOf(inv.getItem(10).getType())))
                    Utils.createItemSquare(inv, Material.GREEN_STAINED_GLASS_PANE, 6, 3);
            } else
                if(craft(inv, p, 7))
                    Utils.createItemSquare(inv, Material.GREEN_STAINED_GLASS_PANE, 6, 3);
        } else
            Utils.createItemSquare(inv, Material.RED_STAINED_GLASS_PANE, 6, 3);

        toReturn.setContents(inv.getContents());
        return toReturn;
    }

    public static void updateWithItem(Player p, int slot, ItemStack item, boolean open) {
        if (!item.hasItemMeta()) {
            Utils.createItemWithoutMeta(inv, item.getType(), item.getAmount(), slot);
        } else {
            Utils.createItemWithMeta(inv, item.getType(), item.getAmount(), slot, item.getItemMeta());
        }
        if (open) p.openInventory(GUI.GUI(p));
    }
}

