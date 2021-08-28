package fr.basiliks.dynamicshop.commands;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.ICommand;
import fr.newzproject.core.inventory.InventoryAPI;
import fr.newzproject.core.inventory.inventory.ClickableItem;
import fr.newzproject.core.inventory.inventory.NInventory;
import fr.newzproject.core.utils.ItemBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ShopEditCommand implements ICommand {

    @Override
    @Command(name = "editshop", permission = "bDynamicShop.shop.edit", inGameOnly = true)
    public void command(CommandArgs args) {
        Player player = args.getPlayer();
        if (args.length() == 0) {
            player.sendMessage("§cArgument(s) §8» §c/editshop <name> <buy/sell> <amount>");
        }

        if (args.length() == 3) {
            ShopManager shopManager = ShopManager.get();

            if (shopManager.getShopByName(args.getArgs(0)) != null) {
                Shop shop = shopManager.getShopByName(args.getArgs(0));

                NInventory nInventory = InventoryAPI.getInstance(ShopPlugin.get()).getInventoryCreator().setSize(shop.getShopSize()).setTitle(shop.getShopName()).create();
                shop.getItems().forEach(shopItem -> nInventory.setItem(shopItem.getSlot(), ClickableItem.of(ItemStack.deserialize(shopItem.getItemStack()), event -> {
                    if (event.getCurrentItem() != null) {
                        if (args.getArgs(1).equalsIgnoreCase("buy")) {
                            event.setCurrentItem(new ItemBuilder(event.getCurrentItem()).setLore("§aBuy §8» §a" + args.getArgs(2),"§aSell §8» §a"+(shopItem.getSellPrice() == 0? "" : shopItem.getSellPrice())).toItemStack());
                            shopItem.setBuyPrice(Integer.parseInt(args.getArgs(2)));
                        } else if (args.getArgs(1).equalsIgnoreCase("sell")) {
                            event.setCurrentItem(new ItemBuilder(event.getCurrentItem()).setLore("§aBuy §8» §a"+(shopItem.getBuyPrice() == 0? "" : shopItem.getBuyPrice()),"§aSell §8» §a" + args.getArgs(2)).toItemStack());
                            shopItem.setSellPrice(Integer.parseInt(args.getArgs(2)));
                        }
                    }
                })));
                nInventory.whenClosed(inventoryCloseEvent -> shopManager.saveShop(shop));

                nInventory.open(args.getPlayer());
            }
        }
    }
}
