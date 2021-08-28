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
import org.bukkit.inventory.ItemStack;

public class ShopOpenCommand implements ICommand {

    @Override
    @Command(name = "shop", inGameOnly = true)
    public void command(CommandArgs args) {
        if (args.length() == 0) {
            args.getSender().sendMessage("§cArgument(s) §8» §c/shop <name>");
        } else if (args.length() == 1) {
            if (ShopManager.get().getShopByName(args.getArgs(0)) != null) {
                Shop shop = ShopManager.get().getShopByName(args.getArgs(0));
                NInventory nInventory = InventoryAPI.getInstance(ShopPlugin.get()).getInventoryCreator().setSize(shop.getShopSize()).setTitle(shop.getShopName()).create();
                shop.getItems().forEach(shopItem -> {
                    if (shopItem.getBuyPrice() == 0 && shopItem.getSellPrice() == 0) {
                        nInventory.setItem(shopItem.getSlot(), ClickableItem.empty(ItemStack.deserialize(shopItem.getItemStack())));
                    } else if (shopItem.getBuyPrice() > 0 && shopItem.getSellPrice() == 0) {
                        nInventory.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8» §a" + shopItem.getBuyPrice(), "§aSell §8»").toItemStack(), event -> {
                            args.getPlayer().getInventory().addItem(new ItemStack(event.getCurrentItem().getType(), 1));
                            args.getPlayer().sendMessage("§8» §cTu viens d'acheter l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice());
                        }));

                    } else if (shopItem.getBuyPrice() == 0 && shopItem.getSellPrice() > 0) {
                        nInventory.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8»", "§aSell §8» §a" + shopItem.getSellPrice()).toItemStack(), event -> {
                            if (args.getPlayer().getInventory().contains(event.getCurrentItem())) {
                                args.getPlayer().getInventory().removeItem(new ItemStack(event.getCurrentItem()));
                                args.getPlayer().sendMessage("§8» §aTu vien de vendre l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice() * event.getCurrentItem().getAmount());
                            }
                        }));

                    } else {
                        nInventory.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8» §a" + shopItem.getBuyPrice(), "§aSell §8» §a" + shopItem.getSellPrice()).toItemStack(), event -> {
                            if (event.isLeftClick()) {
                                args.getPlayer().getInventory().addItem(new ItemStack(event.getCurrentItem().getType(), 1));
                                args.getPlayer().sendMessage("§8» §cTu viens d'acheter l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice());
                            } else if (args.getPlayer().getInventory().contains(event.getCurrentItem())) {
                                args.getPlayer().getInventory().removeItem(new ItemStack(event.getCurrentItem()));
                                args.getPlayer().sendMessage("§8» §aTu vien de vendre l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice() * event.getCurrentItem().getAmount());
                            }
                        }));
                    }
                });
                nInventory.open(args.getPlayer());
            }
        }
    }
}
