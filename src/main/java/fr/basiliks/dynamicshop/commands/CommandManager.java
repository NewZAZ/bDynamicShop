package fr.basiliks.dynamicshop.commands;

import fr.newzproject.core.command.CommandFramework;
import org.bukkit.plugin.Plugin;

public class CommandManager {

    public static void register(Plugin plugin) {
        CommandFramework commandFramework = new CommandFramework(plugin);
        commandFramework.registerCommands(new ShopCreateCommand());
        commandFramework.registerCommands(new ShopOpenCommand());
        commandFramework.registerCommands(new ShopEditCommand());
    }
}
