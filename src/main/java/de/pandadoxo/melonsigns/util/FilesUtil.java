// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 20:44 
// -----------------------

package de.pandadoxo.melonsigns.util;

import com.google.gson.stream.JsonReader;
import de.pandadoxo.melonsigns.Main;
import de.pandadoxo.melonsigns.core.ServerSignConfig;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class FilesUtil {

    private static final File serverSign = new File(Main.getInstance().getDataFolder(), "ServerSignConfig.json");

    public FilesUtil() {

        create();
        load();
    }

    public void create() {
        try {
            if (!serverSign.exists()) {
                serverSign.getParentFile().mkdirs();
                serverSign.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            JsonReader serverSignR = new JsonReader(new FileReader(serverSign));
            Main.setServerSignConfig(JsonUtil.getGson().fromJson(serverSignR, ServerSignConfig.class));
            if (Main.getServerSignConfig() == null) Main.setServerSignConfig(new ServerSignConfig());
            serverSignR.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            PrintWriter serverSignW = new PrintWriter(serverSign, "UTF-8");
            serverSignW.println(JsonUtil.getGson().toJson(Main.getServerSignConfig()));
            serverSignW.flush();
            serverSignW.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
