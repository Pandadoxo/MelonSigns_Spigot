// -----------------------
// Coded by Pandadoxo
// on 18.03.2021 at 07:38 
// -----------------------

package de.pandadoxo.melonsigns.listener;

import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.ServerSign;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.Objects;

public class SignListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player p = event.getPlayer();

        ServerSign sign = Main.getServerSignConfig().getSignByLoc(event.getBlock().getLocation(), true);
        if (sign != null) {
            if (!p.isSneaking()) {
                p.sendMessage(Main.PREFIX + "§7Du musst dich §educken §7um dieses Schild zu zerstören");
                event.setCancelled(true);
                return;
            }

            if (!Main.getDoxperm().has(p, "melonensigns.destroy", true)) {
                return;
            }

            sign.destroy();
            p.sendMessage(Main.PREFIX + "§7Das Schild wurde §ezerstört");
            return;
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (!event.hasBlock() || event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ServerSign sign = Main.getServerSignConfig().getSignByLoc(event.getClickedBlock().getLocation(), false);
        if (sign != null) {
            sign.interact(event.getPlayer());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        ServerSign sign = null;
        for (ServerSign serverSign : Main.getServerSignConfig().serverSigns) {
            if (!Objects.equals(serverSign.getLocation().getWorld(), p.getWorld())) {
                continue;
            }
            double distance;
            if ((distance = Main.distanceHorizontal(serverSign.getLocation().clone().add(.5, .5, .5), event.getTo())) > 1) {
                continue;
            }
            if (sign == null || distance < Main.distanceHorizontal(sign.getLocation().clone().add(.5, .5, .5), event.getTo())) {
                sign = serverSign;
            }
        }

        if (sign != null) {
            Vector vel = p.getLocation().toVector().subtract(sign.getLocation().clone().add(.5, .5, .5).toVector()).multiply(.7);
            vel.setY(.4);
            p.setVelocity(vel);
            p.playSound(p.getLocation(), Sound.ENTITY_WITHER_SHOOT, 0.6f, 1.2f);
        }
    }

}
