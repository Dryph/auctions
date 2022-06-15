package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class ListCommand extends BaseCommand {

    @Command(name = "auction.list", permission = "bandali.command.argument.list")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();

        player.sendMessage(CC.CHAT_BAR);
        for (val bid : Auction.getBids()) {
            player.sendMessage(CC.translate("&aIdentifier&7: &f" + bid.getIdentifier()));
        }
        player.sendMessage(CC.CHAT_BAR);
    }
}
