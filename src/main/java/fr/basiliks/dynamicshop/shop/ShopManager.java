package fr.basiliks.dynamicshop.shop;

import com.moandjiezana.toml.Toml;
import fr.newzproject.core.misc.Task;
import fr.newzproject.core.storage.disk.toml.TomlUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ShopManager {

    private static ShopManager instance;

    private final List<Shop> shops = new ArrayList<>();

    private ShopManager() {
        instance = this;
    }

    public static ShopManager get() {
        return instance == null ? instance = new ShopManager() : instance;
    }


    public void registerShop(){
        Task.asyncDelayed(() -> {
            System.out.println("============ bDynamicShop ============");
            try {
                Files.list(new File("plugins/bDynamicShop/shops").toPath()).forEach(path -> {
                    Toml toml = TomlUtils.getConfiguration(path.toFile());
                    if (toml.to(Shop.class) != null) {
                        Shop shop = toml.to(Shop.class);
                        shops.add(shop);
                        System.out.println(" ");
                        System.out.println("\u001B[32mInitialisation du shop " +
                                "\nNom : " + shop.getShopName() +
                                "\nTaille : " + shop.getShopSize() * 9 +
                                "\nAvec succès\u001B[0m");
                    }else {
                        System.out.println(" ");
                        System.out.println("\u001B[31mErreur le fichier : " + path +
                                " n'a pas pu être initialiser\u001B[0m");
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("======================================");
        });
    }

    public Shop getShopByName(String name) {
        return this.shops.stream().filter(shop -> shop.getShopName().equals(name)).findFirst().orElse(null);
    }

    public void createShop(Shop shop) {
        Task.asyncDelayed(() -> {
            new File("plugins/bDynamicShop/shops").mkdirs();
            File file = new File("plugins/bDynamicShop/shops", shop.getShopName() + ".toml");

            if (!file.exists()) {
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
        });
    }

    public void saveShop(Shop shop) {
        Task.asyncDelayed(() -> {
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
        });
    }

    public List<Shop> getShops() {
        return shops;
    }
}
