// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 20:06 
// -----------------------

package de.pandadoxo.melonsigns.commands;

import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.ServerSign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.RayTraceResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCmd implements CommandExecutor, TabCompleter {

    private final String SYNTAX = Main.WRONG_SYNTAX + "/createsign <ServerName> <DisplayName>";

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player p = (Player) sender;
        if (!Main.getDoxperm().has(p, "melonensigns.create", true)) {
            return true;
        }

        if (args.length < 2) {
            p.sendMessage(SYNTAX);
            return true;
        }

        RayTraceResult rayTraceResult = p.rayTraceBlocks(5);
        if (rayTraceResult == null || rayTraceResult.getHitBlock() == null) {
            p.sendMessage(Main.PREFIX + "§7Bitte schaue ein §eWand-Schild §7an!");
            return true;
        }

        if (!rayTraceResult.getHitBlock().getType().name().contains("WALL_SIGN")) {
            p.sendMessage(Main.PREFIX + "§7Bitte schaue ein §eWand-Schild §7an!");
            return true;
        }

        if (Main.getServerSignConfig().getSignByLoc(rayTraceResult.getHitBlock().getLocation()) != null) {
            p.sendMessage(Main.PREFIX + "§7Diese Schild ist bereits registriert");
            return true;
        }

        String serverName = args[0];
        String displayName = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        ServerSign serverSign;
        Main.getServerSignConfig().serverSigns.add((serverSign = new ServerSign(serverName, displayName, rayTraceResult.getHitBlock().getLocation())));
        serverSign.update();
        Main.getFilesUtil().save();

        p.sendMessage(Main.PREFIX + "§7Das Schild wurde erfolgreich §aregistriert");
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {
        if (!(sender instanceof Player))
            return new ArrayList<>();
        Player p = (Player) sender;
        if (!Main.getDoxperm().has(p, "melonensigns.create")) {
            return new ArrayList<>();
        }
        List<String> tocomplete = new ArrayList<>();
        List<String> complete = new ArrayList<>();

        if (args.length == 1) {
            tocomplete.addAll(Main.getBungeeUtil().getServerNames());
        }

        for (String tc : tocomplete) {
            if (tc.toLowerCase().startsWith(args[args.length - 1].toLowerCase())) {
                complete.add(tc);
            }
        }
        return complete;
    }
}
  
  