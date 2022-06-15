package me.tulio.auction.listener;

import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.EndBidEvent;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.entry.BidPlayers;
import me.tulio.auction.entry.PlayerEditor;
import static me.tulio.auction.util.CC.translate;

import me.tulio.auction.util.CC;
import me.tulio.auction.util.Cooldown;
import me.tulio.auction.util.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Set;

public class AuctionListener implements Listener {

    public AuctionListener() {
        Auction.get().getServer().getPluginManager().registerEvents(this, Auction.get());
    }

    @EventHandler
    public void onEndBids(EndBidEvent event) {
        Auction.getSortedBids().keySet().forEach(bidInfo -> {
            val bidPlayers = Auction.getSortedBids().get(bidInfo);
            if (Auction.get().getBalance().getBalance(bidPlayers.getPlayer()) < bidPlayers.getMoney()) {
                bidPlayers.getPlayer().sendMessage(CC.translate("&cYou don't seem to have enough money"));
                return;
            }
            bidPlayers.getPlayer().getInventory().addItem(bidInfo.getItemStack().clone());
            Auction.get().getBalance().removeBalance(bidPlayers.getPlayer(), bidPlayers.getMoney());
        });
        Bukkit.broadcastMessage(CC.CHAT_BAR);
        Bukkit.broadcastMessage(CC.translate("&cThe auctions are over!"));
        Bukkit.broadcastMessage(CC.CHAT_BAR);
        Auction.get().setCooldown(new Cooldown(0));
        Auction.get().setEnableAuction(false);
        Auction.getAllBids().clear();
        Auction.getSortedBids().clear();
        BidPlayers.getInformation().clear();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        val player = event.getPlayer();
        val message = event.getMessage();
        if (Auction.getEditor().containsKey(PlayerEditor.get(player))) {
            event.setCancelled(true);
            val playerEditor = PlayerEditor.get(player);
            val bid = Auction.getEditor().get(playerEditor);
            val args = message.split(" ");
            int integer;

            try {
                integer = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage(translate("&cPlease insert a valid number"));
                return;
            }

            if (message.contains("cancel")) {
                Auction.getEditor().remove(playerEditor);
                PlayerEditor.remove(player);
                player.sendMessage(translate("&cIt has been successfully canceled"));
                return;
            }

            if (playerEditor.isStartBid()) {
                bid.setStartBid(integer);
                player.sendMessage(translate("&7[&a!&7] &aStarting Bid set correctly"));
                player.sendMessage(translate("&7[&c!&7] &aEnter the increase for each bid"));
                playerEditor.setStartBid(false);
                playerEditor.setAmountBid(true);
            }
            else if (playerEditor.isAmountBid()) {
                bid.setBidAmount(integer);
                player.sendMessage(translate("&7[&a!&7] &aAmount Bid set correctly"));
                playerEditor.setAmountBid(false);
                bid.save();
                Auction.getBids().add(bid);
                Auction.getEditor().remove(playerEditor);
                PlayerEditor.remove(player);
            }
            else {
                System.out.print("Si saltó este error reportar a TulioTriste");
                Auction.getEditor().remove(playerEditor);
                PlayerEditor.remove(player);
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        val player = event.getPlayer();
//        if (BidPlayers.getInformation().containsKey(player.getUniqueId())) {
//            if (Auction.getAllBids().containsValue(BidPlayers.getInformation().get(player.getUniqueId()))) {
//                for (BidInfo bidInfo : Auction.getAllBids().keySet()) {
//                    if (Auction.getAllBids().get(bidInfo).equals(BidPlayers.getInformation().get(player.getUniqueId()))) {
//                        Auction.getAllBids().remove(bidInfo);
//                    }
//                }
//            }
//        }
        BidPlayers.getInformation().remove(event.getPlayer().getUniqueId());
        PlayerEditor.getEditors().remove(event.getPlayer());
    }
}
