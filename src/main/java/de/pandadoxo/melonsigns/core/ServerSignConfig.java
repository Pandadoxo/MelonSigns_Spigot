// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 20:28 
// -----------------------

package de.pandadoxo.melonsigns.core;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.List;

public class ServerSignConfig {

    public List<ServerSign> serverSigns = new ArrayList<>();

    public ServerSign getSignByLoc(Location location) {
        for (ServerSign serverSign : serverSigns) {
            if (serverSign.getLocation().equals(location) || (serverSign.getWallBlock() != null && serverSign.getWallBlock().getLocation().equals(location))) {
                return serverSign;
            }
        }
        return null;
    }

    public ServerSign getSignByLoc(Location location, boolean withWall) {
        if (withWall) return getSignByLoc(location);
        for (ServerSign serverSign : serverSigns) {
            if (serverSign.getLocation().equals(location)) {
                return serverSign;
            }
        }
        return null;
    }


}
