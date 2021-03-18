// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 21:16 
// -----------------------

package de.pandadoxo.melonsigns.core;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;

public class Server {

    private String name;
    private Status status;
    private int currplayers;
    private int maxplayers;

    public Server(String name, Status status, int currplayers, int maxplayers) {
        this.name = name;
        this.status = status;
        this.currplayers = currplayers;
        this.maxplayers = maxplayers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrplayers() {
        return currplayers;
    }

    public void setCurrplayers(int currplayers) {
        this.currplayers = currplayers;
    }

    public int getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public enum Status {
        ONLINE(ChatColor.GREEN, "Online", Material.GREEN_CONCRETE), RESTARTING(ChatColor.RED, "Restarting", Material.RED_CONCRETE),
        OFFLINE(ChatColor.DARK_RED, "Offline", Material.RED_CONCRETE), INGAME(ChatColor.GOLD, "Im Spiel", Material.ORANGE_CONCRETE),
        STARTING(ChatColor.GOLD, "Spiel startet", Material.ORANGE_CONCRETE);

        private final ChatColor statusColor;
        private final String statusMessage;
        private final Material statusBlock;

        Status(ChatColor statusColor, String statusMessage, Material statusBlock) {
            this.statusColor = statusColor;
            this.statusMessage = statusMessage;
            this.statusBlock = statusBlock;
        }

        public ChatColor getColor() {
            return this.statusColor;
        }

        public String getMessage() {
            return this.statusMessage;
        }

        public Material getBlock() {
            return this.statusBlock;
        }
    }

}
