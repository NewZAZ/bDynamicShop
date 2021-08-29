package fr.basiliks.dynamicshop.commands;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.gui.ShopEditInventory;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.Completer;
import fr.newzproject.core.command.ICommand;
import fr.newzproject.core.inventory.InventoryAPI;
import fr.newzproject.core.inventory.inventory.ClickableItem;
import fr.newzproject.core.inventory.inventory.NInventory;
import fr.newzproject.core.utils.ItemBuilder;
import fr.newzproject.core.utils.Validator;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ShopEditCommand implements ICommand {
    private final ShopManager shopManager = ShopManager.get();
    @Override
    @Command(name = "editshop", permission = "bDynamicShop.shop.edit", inGameOnly = true)
    public void command(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.length() == 0) {
            player.sendMessage("§cArgument(s) §8» §c/editshop <name> <buy/sell> <amount>");
        }

        if (args.length() == 3) {


            if (shopManager.getShopByName(args.getArgs(0)) != null && Validator.isInteger(args.getArgs(2))) {
                Shop shop = shopManager.getShopByName(args.getArgs(0));

                new ShopEditInventory(shop, args.getArgs(1), Integer.parseInt(args.getArgs(2))).open(player);
            }
        }
    }

    @Completer(name = "editshop")
    public List<String> tabCompleter(CommandArgs args){
        List<String> list = new ArrayList<>();
        shopManager.getShops().forEach(shop -> list.add(shop.getShopName()));
        return list;
    }
}
