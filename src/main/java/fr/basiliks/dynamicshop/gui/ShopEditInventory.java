package fr.basiliks.dynamicshop.gui;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.inventory.InventoryAPI;
import fr.newzproject.core.inventory.inventory.ClickableItem;
import fr.newzproject.core.inventory.inventory.NInventory;
import fr.newzproject.core.utils.InventoryUtils;
import fr.newzproject.core.utils.ItemBuilder;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

public class ShopEditInventory extends NInventory {
    private final ShopPlugin shopPlugin = ShopPlugin.get();


    public ShopEditInventory(Shop shop, String action, int amount) {
        super(InventoryAPI.getInstance(ShopPlugin.get()), shop.getShopName(), InventoryType.CHEST, shop.getShopSize(), "test2", true, false);

        shop.getItems().forEach(shopItem -> this.setItem(shopItem.getSlot(), ClickableItem.of(ItemStack.deserialize(shopItem.getItemStack()), event -> {
            if (event.getCurrentItem() != null) {
                if (action.equalsIgnoreCase("buy")) {
                    event.setCurrentItem(new ItemBuilder(event.getCurrentItem()).setLore("§aBuy §8» §a" + amount,"§aSell §8» §a"+(shopItem.getSellPrice() == 0? "" : shopItem.getSellPrice())).toItemStack());
                    shopItem.setBuyPrice(amount);
                } else if (action.equalsIgnoreCase("sell")) {
                    event.setCurrentItem(new ItemBuilder(event.getCurrentItem()).setLore("§aBuy §8» §a"+(shopItem.getBuyPrice() == 0? "" : shopItem.getBuyPrice()),"§aSell §8» §a" + amount).toItemStack());
                    shopItem.setSellPrice(amount);
                }
            }
        })));
        this.whenClosed(inventoryCloseEvent -> ShopManager.get().saveShop(shop));
    }
}
