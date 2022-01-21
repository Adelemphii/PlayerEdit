package me.adelemphii.playeredit;

import me.adelemphii.playeredit.commands.PlayerEditCommand;
import me.adelemphii.playeredit.utils.PlayerEditUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class PlayerEdit extends JavaPlugin {

    private static PlayerEdit instance;

    @Override
    public void onEnable() {
        instance = this;

        getCommand("edit").setExecutor(new PlayerEditCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerEditUtils(this), this);
    }

    @Override
    public void onDisable() {
    }

    public static PlayerEdit getInstance() {
        return instance;
    }
}
