package fr.basiliks.dynamicshop.category;

import fr.basiliks.dynamicshop.shop.Shop;
import fr.newzproject.core.inventory.inventory.NInventory;

import java.util.List;

public class Category {

    private final String name;
    private final List<Shop> shops;

    public Category(String name, List<Shop> shops) {
        this.name = name;
        this.shops = shops;
    }

    public String getName() {
        return name;
    }

    public List<Shop> getShops() {
        return shops;
    }
}
