package fr.basiliks.dynamicshop.commands;

import fr.newzproject.core.command.Command;
import fr.newzproject.core.command.CommandArgs;
import fr.newzproject.core.command.ICommand;
import org.bukkit.entity.Player;

public class CategoryCreateCommand implements ICommand {

    @Override
    @Command(name = "createCategory", permission = "bDynamicShop.category.create", inGameOnly = true)
    public void command(CommandArgs args) {
        Player player = args.getPlayer();

        if(args.length() == 0){
            
        }
    }
}
