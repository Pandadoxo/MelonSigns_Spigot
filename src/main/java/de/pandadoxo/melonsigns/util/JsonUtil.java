// -----------------------
// Author ~ IllgiLP
// Rewritten by Pandadoxo
// on 16.08.2020 at 16:40 
// -----------------------

package de.pandadoxo.melonsigns.util;

import com.google.gson.*;
import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.Server;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;
import java.util.logging.Level;

public class JsonUtil {

    private static final Gson gson;

    static {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.setPrettyPrinting();
        gsonBuilder.setExclusionStrategies(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes f) {
                return f.getAnnotation(Exclude.class) != null;
            }

            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
        });
        gsonBuilder.registerTypeAdapter(Location.class, (JsonSerializer<Location>) (location, type, jsonSerializationContext) -> {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("world", location.getWorld().getUID().toString());
            jsonObject.addProperty("x", location.getX());
            jsonObject.addProperty("y", location.getY());
            jsonObject.addProperty("z", location.getZ());
            return jsonObject;
        });

        gsonBuilder.registerTypeAdapter(Location.class, (JsonDeserializer<Location>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                if (jsonObject.has("world") && jsonObject.has("x") && jsonObject.has("y") && jsonObject.has("z")) {
                    try {
                        UUID uuid = UUID.fromString(jsonObject.get("world").getAsString());
                        World world = Bukkit.getWorld(uuid);
                        if (world != null) {
                            return new Location(world, jsonObject.get("x").getAsDouble(), jsonObject.get("y").getAsDouble(), jsonObject.get("z").getAsDouble());
                        }
                    } catch (Exception e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Cannot parse Location (" + jsonObject.toString() + "): " + e.getMessage(),
                                e);
                        return null;
                    }
                }
            }
            return null;
        });

        gsonBuilder.registerTypeAdapter(Server.class, (JsonDeserializer<Server>) (jsonElement, type, jsonDeserializationContext) -> {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = (JsonObject) jsonElement;
                if (jsonObject.has("name") && jsonObject.has("status") && jsonObject.has("currplayers") &&
                        jsonObject.has("maxplayers")) {
                    try {
                        String name = jsonObject.get("name").getAsString();
                        Server.Status status = Server.Status.valueOf(jsonObject.get("status").getAsString().toUpperCase());
                        int currPlayers = jsonObject.get("currplayers").getAsInt();
                        int maxPlayers = jsonObject.get("maxplayers").getAsInt();
                        return new Server(name, status, currPlayers, maxPlayers);
                    } catch (Exception e) {
                        Main.getInstance().getLogger().log(Level.SEVERE, "Cannot parse Server(" + jsonObject.toString() + "): " + e.getMessage(), e);
                        return null;
                    }
                }
            }
            return null;
        });

        gson = gsonBuilder.create();
    }

    public static Gson getGson() {
        return gson;
    }

}
