package me.vannername.enchatingplusplus.GUI;

import me.vannername.enchatingplusplus.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

    private Main plugin;

    public InventoryClickListener(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        try {
            e.getClickedInventory().getType();
        } catch (NullPointerException e1) {
            return;
        }

        if (e.getClickedInventory().getType().equals(InventoryType.CHEST) && e.getView().getTitle().equals(GUI.inv_name)) {
            if (e.getSlot() == 9 || e.getSlot() == 10 || e.getSlot() == 13 || e.getSlot() == 16) {
                if (e.getCurrentItem() == null) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        try {
                            ItemStack item = e.getCurrentItem();
                            if (item.getAmount() > 1) {
                                item.setAmount(item.getAmount() - 1);
                                e.getWhoClicked().getInventory().addItem(item);
                                item.setAmount(1);
                            }
                            GUI.updateWithItem((Player) e.getWhoClicked(), e.getSlot(), item, true);
                        } catch (NullPointerException ignored) {
                        }
                    }, 1L);
                } else if (e.isLeftClick()) {
                    if ((e.getSlot() == 9 && e.getCurrentItem().getType() == Material.ELYTRA) || (e.getSlot() == 10 && e.getInventory().getItem(9).getType() == Material.ELYTRA)) {
                        e.getWhoClicked().getInventory().addItem(e.getClickedInventory().getItem(9));
                        GUI.updateWithItem((Player) e.getWhoClicked(), 9, new ItemStack(Material.AIR), false);
                        e.getWhoClicked().getInventory().addItem(e.getClickedInventory().getItem(10));
                        GUI.updateWithItem((Player) e.getWhoClicked(), 10, new ItemStack(Material.AIR), true);
                    }
                    else if (e.getSlot() != 9) {
                        e.getWhoClicked().getInventory().addItem(e.getCurrentItem());
                        GUI.updateWithItem((Player) e.getWhoClicked(), e.getSlot(), new ItemStack(Material.AIR), true);
                    } else e.setCancelled(true);
                } else e.setCancelled(true);
            } else e.setCancelled(true);
        }
    }
}
