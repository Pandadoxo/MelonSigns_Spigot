// -----------------------
// Coded by Pandadoxo
// on 21.12.2020 at 12:38 
// -----------------------

package de.pandadoxo.melonsigns.util;

import de.pandadoxo.melonsigns.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BungeeUtil {

    private DataInputStream respone;
    private List<String> serverNames = new ArrayList<>();

    public BungeeUtil() {
        Bukkit.getMessenger().registerIncomingPluginChannel(Main.getInstance(), "BungeeCord", (s, player, bytes) -> {
            respone = new DataInputStream(new ByteArrayInputStream(bytes));
            try {
                String subChannel = respone.readUTF();
                if (subChannel.equalsIgnoreCase("GetServers")) {
                    serverNames = Arrays.asList(respone.readUTF().split(", "));
                } else {
                    respone = new DataInputStream(new ByteArrayInputStream(bytes));
                }
            } catch (IOException ignored) {
            }
        });

        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (Bukkit.getOnlinePlayers().size() > 0) {
                sendPluginMessage(Bukkit.getOnlinePlayers().stream().findFirst().get(), writeCommand("GetServers"));
            }
        }, 1, 20 * 30);
    }

    private void sendPluginMessage(Player player, byte[] data) {
        player.sendPluginMessage(Main.getInstance(), "BungeeCord", data);
    }

    public void sendConnectRequest(Player player, String server) {
        sendPluginMessage(player, writeCommand("connectrequest", server));
    }

    private byte[] writeCommand(String command, String... args) {
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            dos.writeUTF(command);
            for (String arg : args) {
                dos.writeUTF(arg);
            }
            return os.toByteArray();
        } catch (IOException ignored) {
        }
        return new byte[0];
    }

    public List<String> getServerNames() {
        return serverNames;
    }
}
