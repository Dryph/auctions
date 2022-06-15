package me.tulio.auction.command.subcommands;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.Cooldown;
import me.tulio.auction.util.command.BaseCommand;
import me.tulio.auction.util.command.Command;
import me.tulio.auction.util.command.CommandArgs;
import me.tulio.auction.util.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DisableCommand extends BaseCommand {

    @Command(name = "auction.disable", permission = "bandali.command.argument.disable")
    @Override
    public void onCommand(CommandArgs command) {
        val player = command.getPlayer();
        val args = command.getArgs();

        if (Auction.get().isEnableAuction()) {
            Auction.get().setCooldown(new Cooldown(0));
            Auction.get().setEnableAuction(false);
            Menu.currentlyOpenedMenus.keySet().forEach(name -> {
                Player target = Bukkit.getPlayer(name);
                target.closeInventory();
            });
            player.sendMessage(CC.translate("&cAuctions has been disabled correctly!"));
        } else {
            player.sendMessage(CC.translate("&cAuctions are already deactivated!"));
        }
    }
}
