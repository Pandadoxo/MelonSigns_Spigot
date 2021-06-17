package de.pandadoxo.melonsigns;

import de.pandadoxo.melonsigns.commands.CreateCmd;
import de.pandadoxo.melonsigns.commands.MotdCmd;
import de.pandadoxo.melonsigns.core.AnimationManager;
import de.pandadoxo.melonsigns.core.ServerSignConfig;
import de.pandadoxo.melonsigns.listener.MessageListener;
import de.pandadoxo.melonsigns.listener.SignListener;
import de.pandadoxo.melonsigns.util.BungeeUtil;
import de.pandadoxo.melonsigns.util.FilesUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public static final String PREFIX = "§8[§l§2Melonen§fSigns§8]§r ";
    public static final String WRONG_SYNTAX = PREFIX + "§7Falscher Syntax! Benutze: §e";

    private static Main instance;
    private static Doxperm doxperm;
    private static FilesUtil filesUtil;
    private static BungeeUtil bungeeUtil;
    private static AnimationManager animationManager;
    private static ServerSignConfig serverSignConfig;

    public static double distanceHorizontal(Location location1, Location location2) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < 3; i++) {
            Location one = location1.clone();
            Location two = location2.clone();
            one.setY(location1.getY() + (i - 1));
            min = Math.min(one.distance(two), min);
        }

        return min;
    }

    @Override
    public void onEnable() {
        instance = this;
        doxperm = new Doxperm(PREFIX);
        bungeeUtil = new BungeeUtil();
        serverSignConfig = new ServerSignConfig();
        filesUtil = new FilesUtil();

        animationManager = new AnimationManager().start();
        Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> animationManager.autoUpdate(true), 5);

        getCommand("createsign").setExecutor(new CreateCmd());
        getCommand("createsign").setTabCompleter(new CreateCmd());
        getCommand("motd").setExecutor(new MotdCmd());

        Bukkit.getPluginManager().registerEvents(new SignListener(), this);
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "melone:lobbysign", new MessageListener());
        Bukkit.getMessenger().registerOutgoingPluginChannel(Main.getInstance(), "BungeeCord");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //filesUtil.save();
    }

    public static Main getInstance() {
        return instance;
    }

    public static Doxperm getDoxperm() {
        return doxperm;
    }

    public static FilesUtil getFilesUtil() {
        return filesUtil;
    }

    public static BungeeUtil getBungeeUtil() {
        return bungeeUtil;
    }

    public static AnimationManager getAnimationManager() {
        return animationManager;
    }

    public static ServerSignConfig getServerSignConfig() {
        return serverSignConfig;
    }

    public static void setServerSignConfig(ServerSignConfig serverSignConfig) {
        Main.serverSignConfig = serverSignConfig;
    }

}
