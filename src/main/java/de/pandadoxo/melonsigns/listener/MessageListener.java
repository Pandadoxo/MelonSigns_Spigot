// -----------------------
// Coded by Pandadoxo
// on 21.12.2020 at 11:00 
// -----------------------

package de.pandadoxo.melonsigns.listener;

import com.google.gson.reflect.TypeToken;
import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.Server;
import de.pandadoxo.melonsigns.core.ServerSign;
import de.pandadoxo.melonsigns.util.JsonUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equalsIgnoreCase("melone:lobbysign")) {
            return;
        }

        List<Server> servers = JsonUtil.getGson().fromJson(new String(message, StandardCharsets.UTF_8), new TypeToken<List<Server>>() {
        }.getType());
        servers.removeIf(Objects::isNull);
        for (ServerSign sign : new ArrayList<>(Main.getServerSignConfig().serverSigns)) {
            for (Server server : servers) {
                if (server.getName().equalsIgnoreCase(sign.getServerName())) {
                    sign.setServer(server);
                    sign.update();
                }
            }
        }
    }
}
