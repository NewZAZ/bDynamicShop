package fr.basiliks.dynamicshop.shop;

import com.moandjiezana.toml.Toml;
import fr.newzproject.core.storage.disk.toml.TomlUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

public class ShopManager {

    private static ShopManager instance;

    private ShopManager() {
        instance = this;
    }

    public static ShopManager get() {
        return instance == null ? instance = new ShopManager() : instance;
    }

    public boolean fileShopExist(String name) {
        return new File("plugins/bDynamicShop/shops", name + ".toml").exists();
    }

    public Shop getShopByName(String name) {
        if (fileShopExist(name)) {
            Toml toml = TomlUtils.getConfiguration(new File("plugins/bDynamicShop/shops", name + ".toml"));
            return toml.to(Shop.class);
        }
        return null;
    }

    public void createShop(Shop shop) {
        new File("plugins/bDynamicShop/shops").mkdirs();
        File file = new File("plugins/bDynamicShop/shops", shop.getShopName() + ".toml");

        if (!fileShopExist(shop.getShopName())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            TomlUtils.write(shop, file);
            Bukkit.getConsoleSender().sendMessage("§8» §6Création du nouveau shop avec succès");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveShop(Shop shop) {
        if (getShopByName(shop.getShopName()) == null) {
            createShop(shop);
            return;
        }

        File file = new File("plugins/bDynamicShop/shops", shop.getShopName() + ".toml");
        try {
            TomlUtils.write(shop, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
