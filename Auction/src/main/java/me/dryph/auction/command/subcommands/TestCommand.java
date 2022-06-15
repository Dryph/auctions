package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class TestCommand extends BaseCommand {

    @Command(name = "auction.test")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();

        Auction.getAllBids().forEach((bidInfo, bidPlayers) -> System.out.print(bidInfo.identifier + "   " + bidPlayers.getPlayer().getName()));
    }
}
