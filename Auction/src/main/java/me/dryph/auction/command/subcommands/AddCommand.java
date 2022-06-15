package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.entry.PlayerEditor;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class AddCommand extends BaseCommand {

    @Command(name = "auction.add", permission = "bandali.command.argument.add")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();
        val args = command.getArgs();

        if (args.length == 0) {
            player.sendMessage(CC.translate("&cPlease insert identifier!"));
            return;
        }

        if (player.getItemInHand() == null) {
            player.sendMessage(CC.translate("&cYou must have a valid item in your hand"));
            return;
        }

        val identifier = args[0];
        val bid = new BidInfo(identifier, player.getItemInHand());
        val playerEditor = new PlayerEditor(player);
        playerEditor.setStartBid(true);
        PlayerEditor.getEditors().put(player, playerEditor);
        Auction.getEditor().put(playerEditor, bid);
        player.sendMessage(CC.translate("&7[&c!&7] &aPlease insert the starting bid."));
    }
}
