package fr.basiliks.dynamicshop.commands;

import fr.basiliks.dynamicshop.shop.ShopManager;
import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.Completer;
import fr.newzproject.core.command.ICommand;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

public class ShopListCommand implements ICommand {

    @Override
    @Command(name = "listShop", permission = "bDynamicShop.shop.list", inGameOnly = true)
    public void command(CommandArgs args) {

        args.getSender().sendMessage("§8§m-----------------§d[ §d§lbDynamicShop §d]§8§m-----------------");
        ShopManager.get().getShops().forEach(shop -> {
            TextComponent textComponent = new TextComponent("§d"+shop.getShopName());
            ComponentBuilder componentBuilder = new ComponentBuilder("§8§m------------§d[ §d§lInformations §d]§8§m------------")
                    .append("\n")
                    .append("\n")
                    .append("§8» §dNom : "+shop.getShopName())
                    .append("\n")
                    .append("\n")
                    .append("§8» §dTaille : "+shop.getShopSize() *9)
                    .append("\n")
                    .append("\n")
                    .append("§8» §cClique ici pour ouvrir le shop !");
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,componentBuilder.create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/shop "+shop.getShopName()));
            args.getPlayer().spigot().sendMessage(textComponent);
        });
        args.getSender().sendMessage("§8§m-------------------------------------------------");
    }

}
