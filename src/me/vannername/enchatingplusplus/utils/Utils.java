package me.vannername.enchatingplusplus.utils;

import me.vannername.enchatingplusplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Utils {
    public static void sendTestMessage(Player p) {
        p.sendMessage("This is a test message");
    }

    public static List<String> loreAdd(List<String> lore, String... lorestring) {
        for (String s : lorestring) {
            lore.add(s);
        }
        return lore;
    }

    public static String enchants_stats(int n, String type) {
        if (type.equals("s")) {
            switch (n) {
                case 1:
                    return "+2 damage";
                case 2:
                    return "+25% attack speed";
                case 3:
                    return "On kill all drops around you are picked up automatically.";
                case 4:
                    return "On hit your enemy gets \"Slowness 1\" for 5 seconds";
                case 5:
                    return "+4 damage";
                case 6:
                    return "While holding you move 25% faster";
                case 7:
                    return "+20% more attack speed";
                case 8:
                    return "On hit your enemy also gets \"Wither 1\" for 5 seconds";
                case 9:
                    return "On kill, you get \"Regeneration 1\" for the duration proportional to the damage you dealt to the enemy (max = 15s)";
            }
        }
        else if (type.equals("a")) {
            switch (n) {
                case 1:
                    return "+2 defence points";
                case 2:
                    return "+1 armor toughness";
            }
        }
        else if (type.equals("p")) {
            switch (n) {
                case 1:
                    return "While holding the pickaxe you get \"Haste 1\"";
                case 2:
                    return "While holding you move 25% faster";
            }
        }
        return "";
    }
    public static String enchants_text(int n, String type) {
        if (type.equals("s")) {
            switch (n) {
                case 1:
                    return "Your sword was enchanted with a powerful spell!";
                case 2:
                    return "Your sword was forged to become lighter!";
                case 3:
                    return "Your sword has acquired magnetism!";
                case 4:
                    return "Your sword won`t let your opponents flee their destiny!";
                case 5:
                    return "Your sword was further empowered!";
                case 6:
                    return "Your sword makes you feel like a cheetah!";
                case 7:
                    return "Your sword now attack faster than the hurricane!";
                case 8:
                    return "Your sword now slowly tears your opponents from the inside!";
                case 9:
                    return "Your sword now steals the health of the enemy!";
            }
        }
        else if (type.equals("a")) {
            switch (n) {
                case 1:
                    return "This piece of armor feels stronger!";
                case 2:
                    return "This piece of armor feels tougher!";
            }
        }
        else if (type.equals("p")) {
            switch (n) {
                case 1:
                    return "Your pickaxe feels stronger!";
                case 2:
                    return "Your pickaxe makes you fast as fuck!";
            }
        }
        return "";

    }

//    public static void effectCheck(Player p, PotionEffect pt) {
//        if(p.hasPotionEffect(pt.getType()) && p.getPotionEffect(pt.getType()).getAmplifier() == 0 && p.getPotionEffect(pt.getType()).getDuration() > 20) {
//            p.addPotionEffect(new PotionEffect(pt.getType(), 40, 1, pt.isAmbient(), pt.hasParticles(), pt.hasIcon()));
//        } else {
//            p.addPotionEffect(pt);
//        }
//    }
//    public static void effectCheck(Player p, PotionEffect pt) {
//        if(p.hasPotionEffect(pt.getType())) {
//            p.addPotionEffect(new PotionEffect(pt.getType(), 40, amplifier, pt.isAmbient(), pt.hasParticles(), pt.hasIcon()));
//        } else {
//            p.addPotionEffect(pt);
//        }
//    }


    // no longer needed
    /*
    public static void armorEffects(Player p) {
        for (ItemStack i : p.getInventory().getArmorContents()) {
            if (i != null) {
                try {
                    if(i.getItemMeta().hasCustomModelData()) {
                        int cmd = i.getItemMeta().getCustomModelData();
                        if (cmd == 11)
                            Utils.effectCheck(p, new PotionEffect(PotionEffectType.WATER_BREATHING, 20, 0, false, false, true));
                        if (cmd == 12)
                            Utils.effectCheck(p, new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0, false, false, true));
                        if (cmd == 13)
                            Utils.effectCheck(p, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20, 0, false, false, true));
                        if (cmd == 14)
                            Utils.effectCheck(p, new PotionEffect(PotionEffectType.JUMP, 20, 0, false, false, true));
                    }
                } catch (NullPointerException ignored) {}
            }
        }
    }
    */

    public static ItemStack createItemWithMeta(Inventory inv, Material material, int amount, int invSlot, ItemMeta meta){
        ItemStack item;
        item = new ItemStack(material, amount);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createItemBasic(Inventory inv, Material material, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList();
        item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s : loreString) {
            lore.add(s);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot, item);
        return item;
    }

    public static ItemStack createItemWithoutMeta(Inventory inv, Material material, int amount, int invSlot){
        ItemStack item = new ItemStack(material, amount);
        inv.setItem(invSlot, item);
        return item;
    }

    public static void createItemSquare(Inventory inv, Material material, int startSlot, int size) {
        ItemStack item = new ItemStack(material, 1);
        int i = startSlot;
        for (; i < startSlot + size; i++) inv.setItem(i, item);
        i--;
        for (int j = 1; j < size; j++) {i += 9; inv.setItem(i, item);}
        for (; i >= startSlot + 9*(size - 1); i--) inv.setItem(i, item);
        i++;
        for (int j = size; j > 1; j--) {i -= 9; inv.setItem(i, item);}
    }
}

