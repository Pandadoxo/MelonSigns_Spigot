// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 21:55 
// -----------------------

package de.pandadoxo.melonsigns.commands;

import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.ServerSign;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

public class MotdCmd implements CommandExecutor {

    private final String SYNTAX = Main.WRONG_SYNTAX + "/motd <Motd>";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;
        if (!Main.getDoxperm().has(p, "melonensigns.motd", true)) {
            return true;
        }

        if (args.length < 1) {
            p.sendMessage(SYNTAX);
            return true;
        }

        RayTraceResult rayTraceResult = p.rayTraceBlocks(5);
        if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) {
            p.sendMessage(Main.PREFIX + "§7Bitte schaue ein §eServer-Schild §7an!");
            return true;
        }

        if (!rayTraceResult.getHitBlock().getType().name().contains("WALL_SIGN")) {
            p.sendMessage(Main.PREFIX + "§7Bitte schaue ein §eServer-Schild §7an!");
            return true;
        }
        ServerSign serverSign;
        if ((serverSign = Main.getServerSignConfig().getSignByLoc(rayTraceResult.getHitBlock().getLocation())) == null) {
            p.sendMessage(Main.PREFIX + "§7Diese Schild ist kein §eServer-Schild");
            return true;
        }

        serverSign.setMotd(String.join(" ", args));
        p.sendMessage(Main.PREFIX + "§7Die MOTD für das §eServer-Schild §7wurde gesetzt");
        p.sendMessage(Main.PREFIX + "§7MOTD: §r" + ChatColor.translateAlternateColorCodes('&', serverSign.getMotd()));
        Main.getFilesUtil().save();
        return false;
    }


}