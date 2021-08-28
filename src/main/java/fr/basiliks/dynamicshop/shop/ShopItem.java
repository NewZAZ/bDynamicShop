package fr.basiliks.dynamicshop.shop;

import java.util.Map;

public class ShopItem {

    private final int slot;
    private final Map<String, Object> itemStack;
    private int buyPrice;
    private int sellPrice;

    public ShopItem(int slot, Map<String, Object> itemStack, int buyPrice, int sellPrice) {
        this.slot = slot;
        this.itemStack = itemStack;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public int getSlot() {
        return slot;
    }

    public Map<String, Object> getItemStack() {
        return itemStack;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(int buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Override
    public String toString() {
        return "ShopItem{" +
                "slot=" + slot +
                ", itemStack=" + itemStack +
                ", buyPrice=" + buyPrice +
                ", sellPrice=" + sellPrice +
                '}';
    }
}
