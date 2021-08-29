package fr.basiliks.dynamicshop.category;

import fr.basiliks.dynamicshop.shop.Shop;
import fr.newzproject.core.misc.Task;
import fr.newzproject.core.storage.disk.toml.TomlUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    private final List<Category> categories = new ArrayList<>();

    public void createCategory(Category category) {
        Task.asyncDelayed(() -> {
            new File("plugins/bDynamicShop/category").mkdirs();
            File file = new File("plugins/bDynamicShop/category", category.getName() + ".toml");

            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                TomlUtils.write(category, file);
                Bukkit.getConsoleSender().sendMessage("§8» §6Création du nouveau shop avec succès");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public Category getCategoryByName(String name) {
        return this.categories.stream().filter(shop -> shop.getName().equals(name)).findFirst().orElse(null);
    }

    public void saveCategory(Category category) {
        Task.asyncDelayed(() -> {
            if (getCategoryByName(category.getName()) == null) {
                createCategory(category);
                return;
            }

            File file = new File("plugins/bDynamicShop/category", category.getName() + ".toml");
            try {
                TomlUtils.write(category, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
