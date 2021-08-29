package fr.basiliks.dynamicshop.gui;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.newzproject.core.inventory.InventoryAPI;
import fr.newzproject.core.inventory.InventoryCreator;
import fr.newzproject.core.inventory.inventory.ClickableItem;
import fr.newzproject.core.inventory.inventory.NInventory;
import fr.newzproject.core.utils.InventoryUtils;
import fr.newzproject.core.utils.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class ShopInventory extends NInventory {
    private final ShopPlugin shopPlugin = ShopPlugin.get();
    private final Economy econ = shopPlugin.getEcon();

    public ShopInventory(Shop shop, Player player) {

        super(InventoryAPI.getInstance(ShopPlugin.get()), shop.getShopName(), InventoryType.CHEST, shop.getShopSize(), "test", true, false);
        shop.getItems().forEach(shopItem -> {
            if (shopItem.getBuyPrice() == 0 && shopItem.getSellPrice() == 0) {
                this.setItem(shopItem.getSlot(), ClickableItem.empty(ItemStack.deserialize(shopItem.getItemStack())));
            } else if (shopItem.getBuyPrice() > 0 && shopItem.getSellPrice() == 0) {
                this.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8» §a" + shopItem.getBuyPrice(), "§aSell §8»").toItemStack(), event -> {
                    if (econ.getBalance(player) >= shopItem.getBuyPrice()) {
                        if(InventoryUtils.hasSpace(player.getInventory())) {
                            player.getInventory().addItem(new ItemStack(event.getCurrentItem().getType(), 1));
                            player.sendMessage("§8» §cTu viens d'acheter l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice());
                            econ.withdrawPlayer(player, shopItem.getBuyPrice());
                        }else {
                            this.close(player);
                            player.sendMessage("§8» §cErreur : Votre inventaire est complet !");
                        }
                    } else {
                        this.close(player);
                        player.sendMessage("§8» §cErreur : Vous n'avez pas l'argent requis (" + shopItem.getBuyPrice() + ") !");
                    }
                }));
            } else if (shopItem.getBuyPrice() == 0 && shopItem.getSellPrice() > 0) {
                this.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8»", "§aSell §8» §a" + shopItem.getSellPrice()).toItemStack(), event -> {
                    if (InventoryUtils.getAmountItems(player.getInventory(), event.getCurrentItem()) != 0) {
                        player.getInventory().removeItem(new ItemStack(event.getCurrentItem().getType(), 1));
                        player.sendMessage("§8» §aTu vien de vendre l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getSellPrice());
                        econ.depositPlayer(player, shopItem.getSellPrice());
                    } else {
                        this.close(player);
                        player.sendMessage("§8» §cErreur : Vous n'avez pas les items requis !");
                    }
                }));

            } else {
                this.setItem(shopItem.getSlot(), ClickableItem.of(new ItemBuilder(ItemStack.deserialize(shopItem.getItemStack())).setLore("§aBuy §8» §a" + shopItem.getBuyPrice(), "§aSell §8» §a" + shopItem.getSellPrice()).toItemStack(), event -> {
                    if (event.isLeftClick()) {
                        if (econ.getBalance(player) >= shopItem.getBuyPrice()) {
                            player.getInventory().addItem(new ItemStack(event.getCurrentItem().getType(), 1));
                            player.sendMessage("§8» §cTu viens d'acheter l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice());
                            econ.withdrawPlayer(player, shopItem.getBuyPrice());
                        } else {
                            this.close(player);
                            player.sendMessage("§8» §cErreur : Vous n'avez pas l'argent requis (" + shopItem.getBuyPrice() + ") !");
                        }
                    } else {
                        if (InventoryUtils.getAmountItems(player.getInventory(), event.getCurrentItem()) != 0) {
                            player.getInventory().removeItem(new ItemStack(event.getCurrentItem().getType(), 1));
                            player.sendMessage("§8» §aTu vien de vendre l'item : " + event.getCurrentItem().getType().name() + " pour " + shopItem.getBuyPrice());
                            econ.depositPlayer(player, shopItem.getSellPrice());
                        } else {
                            this.close(player);
                            player.sendMessage("§8» §cErreur : Vous n'avez pas les items requis !");
                        }
                    }
                }));
            }
        });
        this.open(player);
    }
    
}
