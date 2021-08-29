package fr.basiliks.dynamicshop.commands;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.gui.ShopInventory;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.basiliks.dynamicshop.shop.ShopItem;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.Completer;
import fr.newzproject.core.command.ICommand;
import fr.newzproject.core.inventory.InventoryAPI;
import fr.newzproject.core.inventory.inventory.ClickableItem;
import fr.newzproject.core.inventory.inventory.NInventory;
import fr.newzproject.core.utils.InventoryUtils;
import fr.newzproject.core.utils.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.ArrayList;
import java.util.List;

public class ShopOpenCommand implements ICommand {
    private Economy econ = null;

    private boolean setupEconomy() {
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager()
                .getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            econ = economyProvider.getProvider();
        }

        return (econ != null);
    }

    @Override
    @Command(name = "shop", inGameOnly = true)
    public void command(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.length() == 0) {
            args.getSender().sendMessage("§cArgument(s) §8» §c/shop <name>");
        } else if (args.length() == 1) {
            if (ShopManager.get().getShopByName(args.getArgs(0)) != null) {
                if (setupEconomy()) {
                    Shop shop = ShopManager.get().getShopByName(args.getArgs(0));
                    new ShopInventory(shop, player);
                }
            }
        }
    }

    @Completer(name = "shop")
    public List<String> tabCompleter(CommandArgs args){
        List<String> list = new ArrayList<>();
        ShopManager.get().getShops().forEach(shop -> list.add(shop.getShopName()));
        return list;
    }
}
