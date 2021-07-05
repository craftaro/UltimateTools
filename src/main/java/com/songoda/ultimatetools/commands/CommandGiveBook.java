package com.songoda.ultimatetools.commands;

import com.songoda.core.commands.AbstractCommand;
import com.songoda.ultimatetools.UltimateTools;
import com.songoda.ultimatetools.enchant.AbstractEnchant;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class CommandGiveBook extends AbstractCommand {

    final UltimateTools plugin;

    public CommandGiveBook(UltimateTools plugin) {
        super(false, "givebook");
        this.plugin = plugin;
    }

    @Override
    protected ReturnType runCommand(CommandSender sender, String... args) {
        if (args.length == 0)
            return ReturnType.SYNTAX_ERROR;

        AbstractEnchant enchant = plugin.getEnchantManager().getEnchant(args[0]);

        if (enchant == null) {
            plugin.getLocale().newMessage("&cThe enchant " + args[0] + " does not exist.").sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        }

        if (args.length == 1) {
            if (sender instanceof Player) {
                ((Player) sender).getInventory().addItem(enchant.getBook());
                return ReturnType.SUCCESS;
            }
        } else if (Bukkit.getPlayerExact(args[1]) == null) {
            plugin.getLocale().newMessage("&cThat username does not exist, or the user is not online!")
                    .sendPrefixedMessage(sender);
            return ReturnType.FAILURE;
        } else {
            Bukkit.getPlayerExact(args[1]).getInventory().addItem(enchant.getBook());
            return ReturnType.SUCCESS;
        }
        return ReturnType.FAILURE;
    }

    @Override
    protected List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 1) {
            return plugin.getEnchantManager().getEnchants().stream()
                    .map(AbstractEnchant::getIdentifyingType).collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public String getPermissionNode() {
        return "ultimatetools.givebook";
    }

    @Override
    public String getSyntax() {
        return "givebook <enchant> [player]";
    }

    @Override
    public String getDescription() {
        return "Gives an enchant book to you or a player.";
    }
}
