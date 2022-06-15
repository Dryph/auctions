package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;

public class HelpCommand extends BaseCommand {

    @Command(name = "auction.help", permission = "bandali.command.argument.help")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();

        player.sendMessage(CC.CHAT_BAR);
        player.sendMessage(CC.translate("&6&lAuction &6Arguments"));
        player.sendMessage("");
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction"));
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction help"));
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction enable"));
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction information"));
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction add"));
        player.sendMessage(CC.translate(" &7\u27A4 &e/auction list"));
        player.sendMessage(CC.CHAT_BAR);
    }
}
