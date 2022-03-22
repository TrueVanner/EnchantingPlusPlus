package me.vannername.enchatingplusplus.utils;

import org.bukkit.entity.Player;

public class PlayerPassive {
    private Player p;
    private boolean passive = false;

    private void checkPassive() {
        p.getInventory().forEach(i -> {
            try {
                if (i.getItemMeta().getCustomModelData() == 999) {
                    passive = true;
                }
            } catch (NullPointerException | IllegalStateException ignored) {}
        });
    }
    public PlayerPassive(Player p) {
        this.p = p;
        checkPassive();
    }

    public boolean isPassive() {
        return passive;
    }
}
