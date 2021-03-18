// -----------------------
// Coded by Pandadoxo
// on 17.03.2021 at 21:31 
// -----------------------

package de.pandadoxo.melonsigns.core;

import de.pandadoxo.melonsigns.Main;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class AnimationManager {

    private BukkitTask bukkitTask = null;
    private int currentFrame;
    private boolean autoUpdate;

    public AnimationManager start() {
        if (bukkitTask != null) {
            return this;
        }

        bukkitTask = new BukkitRunnable() {

            @Override
            public void run() {
                currentFrame++;
                if (autoUpdate) {
                    for (ServerSign serverSign : Main.getServerSignConfig().serverSigns) {
                        serverSign.update();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
        return this;
    }

    public AnimationManager stop() {
        if (bukkitTask != null) {
            bukkitTask.cancel();
            currentFrame = 0;
        }
        return this;
    }

    public AnimationManager autoUpdate(boolean autoUpdate) {
        this.autoUpdate = autoUpdate;
        return this;
    }

    public String getOfflineAnimation() {
        String animation = "";
        int correctFrames = currentFrame % 80;
        if (correctFrames >= 70) {
            animation = "§8   o         ";
        } else if (correctFrames >= 60) {
            animation = "§8      o      ";
        } else if (correctFrames >= 50) {
            animation = "§8         o   ";
        } else if (correctFrames >= 40) {
            animation = "§8            o";
        } else if (correctFrames >= 30) {
            animation = "§8         o   ";
        } else if (correctFrames >= 20) {
            animation = "§8      o      ";
        } else if (correctFrames >= 10) {
            animation = "§8   o         ";
        } else if (correctFrames >= 0) {
            animation = "§8o            ";
        }
        return animation;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
