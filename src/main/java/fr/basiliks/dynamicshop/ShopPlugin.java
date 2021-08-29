package fr.basiliks.dynamicshop;

import fr.basiliks.dynamicshop.commands.CommandManager;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.NCore;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ShopPlugin extends JavaPlugin {

    private static ShopPlugin instance;

    private Economy econ = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }


    public static ShopPlugin get() {
        return instance;
    }

    @Override
    public void onEnable() {
        NCore.register(this);
        CommandManager.register(this);
        instance = this;
        ShopManager.get().registerShop();
        setupEconomy();
    }

    public Economy getEcon() {
        return econ;
    }
}
