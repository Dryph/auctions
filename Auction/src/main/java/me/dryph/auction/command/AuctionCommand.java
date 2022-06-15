package me.tulio.auction.command;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.command.subcommands.*;
import me.tulio.auction.menu.UsersMenu;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;

public class AuctionCommand extends BaseCommand {

    public AuctionCommand() {
        super();
        new HelpCommand();
        new EnableCommand();
        new DisableCommand();
        new AddCommand();
        new InformationCommand();
        new ListCommand();
        new TestCommand();
    }

    @Command(name = "auction", aliases = {"ac", "subastas"})
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();

        if (Auction.get().isEnableAuction()) new UsersMenu(Auction.get()).openMenu(player);
        else player.sendMessage(CC.translate("&cAuctions are temporarily disabled"));
    }
}
