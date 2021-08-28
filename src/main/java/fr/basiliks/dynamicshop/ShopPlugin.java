package fr.basiliks.dynamicshop;

import fr.basiliks.dynamicshop.commands.CommandManager;
import fr.newzproject.core.NCore;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlugin extends JavaPlugin {

    private static ShopPlugin instance;

    public static ShopPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {
        NCore.register(this);
        CommandManager.register(this);
        instance = this;
    }
}
