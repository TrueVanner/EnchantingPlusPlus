package me.vannername.enchatingplusplus;

import me.vannername.enchatingplusplus.GUI.GUI;
import me.vannername.enchatingplusplus.GUI.InventoryClickListener;
import me.vannername.enchatingplusplus.listeners.Listeners;
//import me.vannername.enchatingplusplus.listeners.ItemLocator;
import me.vannername.enchatingplusplus.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        saveDefaultConfig();
        //new ItemLocator(this); No longer needed
        Listeners.loadForbidden();
        new Listeners(this);
        new InventoryClickListener(this);
        GUI.init();

        // Listeners.testForbidden(getServer().getConsoleSender());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player p)) {
            return false;
        }
        p.openInventory(GUI.GUI(p));
        return true;
    }
}

