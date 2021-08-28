package fr.basiliks.dynamicshop.commands;

import fr.basiliks.dynamicshop.ShopPlugin;
import fr.basiliks.dynamicshop.shop.Shop;
import fr.basiliks.dynamicshop.shop.ShopItem;
import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.ICommand;
import fr.newzproject.core.misc.EventListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;

public class ShopCreateCommand implements ICommand {

    @Override
    @Command(name = "createShop", permission = "bDynamicShop.shop.create", inGameOnly = true)
    public void command(CommandArgs args) {
        if (args.length() == 0) {
            args.getPlayer().sendMessage("§cArgument(s) §8» §c/createShop <name>");
        }
        if (args.length() == 1) {
            new EventListener<>(ShopPlugin.get(), PlayerInteractEvent.class, (eventListener, event) -> {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Block block = event.getClickedBlock();
                    if (block.getType() != Material.AIR && (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST)) {
                        args.getPlayer().sendMessage("§8» §6Création du shop " + args.getArgs(0) + " en cours...");

                        Chest chest = (Chest) block.getState();
                        List<ShopItem> shopItems = new LinkedList<>();
                        int slot = 0;

                        for (ItemStack content : chest.getInventory().getContents()) {
                            if (content != null && content.getType() != Material.AIR) {
                                shopItems.add(new ShopItem(slot,
                                        content.serialize()
                                        , 0, 0));
                            }
                            slot++;
                        }

                        ShopManager.get().createShop(new Shop(
                                args.getArgs(0),
                                chest.getInventory().getSize() / 9,
                                shopItems));
                        eventListener.unregister();
                        event.setCancelled(true);
                    }

                }
            });
        }
    }
}
