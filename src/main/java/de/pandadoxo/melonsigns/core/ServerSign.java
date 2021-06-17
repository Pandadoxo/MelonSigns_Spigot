// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 20:22 
// -----------------------

package de.pandadoxo.melonsigns.core;

import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.util.Exclude;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.WallSign;
import org.bukkit.entity.Player;

public class ServerSign {

    private String serverName;
    private String displayName;
    private String motd;
    private Location location;

    @Exclude
    private Server server;

    public ServerSign(String serverName, String displayName, String motd, Location location) {
        this.serverName = serverName;
        this.displayName = displayName;
        this.motd = motd;
        this.location = location;
        this.server = new Server(null, Server.Status.OFFLINE, 0, 0);
    }

    public ServerSign(String serverName, String displayName, Location location) {
        this.serverName = serverName;
        this.displayName = displayName;
        this.location = location;
        this.motd = "";
        this.server = new Server(null, Server.Status.OFFLINE, 0, 0);
    }

    public void interact(Player player) {
        if (server.getStatus() == Server.Status.RESTARTING || server.getStatus() == Server.Status.OFFLINE) {
            player.sendMessage("§c§oDieser Server ist momentan nicht erreichbar");
            return;
        }
        if (server.getStatus() == Server.Status.STARTING) {
            player.sendMessage("§c§oDas Spiel startet bereits");
            return;
        }

        Main.getBungeeUtil().sendConnectRequest(player, serverName);
    }

    public void update() {
        Block block = location.getBlock();
        if (!block.getType().name().contains("WALL_SIGN")) {
            destroy();
            return;
        }
        if (server == null) {
            server = new Server(null, Server.Status.OFFLINE, 0, 0);
        }

        Sign sign = (Sign) block.getState();
        if (server.getStatus().equals(Server.Status.OFFLINE) || server.getStatus().equals(Server.Status.RESTARTING)) {
            sign.setLine(0, "§e" + ChatColor.translateAlternateColorCodes('&', displayName));
            sign.setLine(1, "§8[" + server.getStatus().getColor() + server.getStatus().getMessage() + "§8]");
            sign.setLine(2, "");
            sign.setLine(3, Main.getAnimationManager().getOfflineAnimation());
        } else {
            if (motd == null || motd.equals("")) {
                sign.setLine(0, "§e" + ChatColor.translateAlternateColorCodes('&', displayName));
                sign.setLine(1, "§8[" + server.getStatus().getColor() + server.getStatus().getMessage() + "§8]");
                sign.setLine(2, "§7" + server.getCurrplayers() + "§8/§7" + server.getMaxplayers());
                sign.setLine(3, "");
            } else {
                sign.setLine(0, "§e" + ChatColor.translateAlternateColorCodes('&', displayName));
                sign.setLine(1, "§8[" + server.getStatus().getColor() + server.getStatus().getMessage() + "§8]");
                sign.setLine(2, "§8" + ChatColor.translateAlternateColorCodes('&', motd));
                sign.setLine(3, "§7" + server.getCurrplayers() + "§8/§7" + server.getMaxplayers());
            }
        }
        sign.update();
        if (getWallBlock() != null) getWallBlock().setType(server.getStatus().getBlock());
    }

    public void destroy() {
        Main.getServerSignConfig().serverSigns.remove(this);
    }

    public Block getWallBlock() {
        if (location.getBlock().getType().name().contains("WALL_SIGN")) {
            WallSign wallSign = (WallSign) location.getBlock().getBlockData();
            return location.getBlock().getRelative(wallSign.getFacing().getOppositeFace());
        } else {
            destroy();
            return null;
        }
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public final Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
}
