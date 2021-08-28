package fr.basiliks.dynamicshop.shop;

import java.util.List;

public class Shop {

    private final String shopName;
    private final int shopSize;
    private final List<ShopItem> items;

    public Shop(String shopName, int shopSize, List<ShopItem> items) {
        this.shopName = shopName;
        this.shopSize = shopSize;
        this.items = items;
    }

    public String getShopName() {
        return shopName;
    }

    public int getShopSize() {
        return shopSize;
    }

    public List<ShopItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Shop{" +
                "shopName='" + shopName + '\'' +
                ", shopSize=" + shopSize +
                ", items=" + items +
                '}';
    }

}
