package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.runnable.BidRunnable;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.Cooldown;
import me.tulio.auction.util.TaskUtil;
import me.tulio.auction.util.TimeUtil;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;
import org.bukkit.entity.Player;

public class EnableCommand extends BaseCommand {

    @Command(name = "auction.enable", permission = "bandali.command.argument.enable")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();
        val args = command.getArgs();

        if (Auction.get().isEnableAuction()) {
            player.sendMessage(CC.translate("&cAuctions are already activated!"));
        } else {
            if (args.length == 0) {
                player.sendMessage(CC.translate("&cUsage: /auction enable (time)"));
                return;
            }
            Auction.get().setEnableAuction(true);
            val time = TimeUtil.parseTime(args[0]) + 1000L;
            val cooldown = new Cooldown(time);
            TaskUtil.runTimer(new BidRunnable(), 0L, 20L);
            Auction.get().setCooldown(cooldown);
            player.sendMessage(CC.translate("&cAuctions enabled!"));
        }
    }
}
