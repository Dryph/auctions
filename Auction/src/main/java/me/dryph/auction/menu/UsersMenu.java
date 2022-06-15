package me.tulio.auction.menu;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.val;
import me.tulio.auction.Auction;
import me.tulio.auction.EndBidEvent;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.entry.BidPlayers;
import me.tulio.auction.util.CC;
import me.tulio.auction.util.ItemBuilder;
import me.tulio.auction.util.TimeUtil;
import me.tulio.auction.util.menu.Button;
import me.tulio.auction.util.menu.pagination.JumpToPageButton;
import me.tulio.auction.util.menu.pagination.PageButton;
import me.tulio.auction.util.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class UsersMenu extends PaginatedMenu {

    public UsersMenu(Plugin plugin) {
        super(plugin);
    }

    {
        setAutoUpdate(true);
        setUpdateAfterClick(true);
    }

    @Override
    public int getSize() {
        return 6*9;
    }

    @Override
    public int getMaxItemsPerPage(Player player) {
        return 34;
    }

    @Override
    public String getPrePaginatedTitle(Player player) {
        return "&cAuctions";
    }

    @Override
    public Map<Integer, Button> getAllPagesButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        int slot = 0;
        for (int b = 0; b < Auction.getBids().size(); b++) {
            val bid = Auction.getBids().get(b);
            if (slot == 7) slot++;
            if (slot == 8) slot++;
            if (slot == 16) slot++;
            if (slot == 17) slot++;
            if (slot == 25) slot++;
            if (slot == 26) slot++;
            buttons.put(slot++, new ItemButton(bid));
        }
        return buttons;
    }

    @Override
    public Map<Integer, Button> getGlobalButtons(Player player) {
        Map<Integer, Button> buttons = Maps.newHashMap();
        Button none = new Button() {
            @Override
            public ItemStack getButtonItem(Player player) {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).data(9).build();
            }
        };
        buttons.put(53, new PageButton(1, this));
        buttons.put(49, new TimeLeftButton());
        buttons.put(45, new PageButton(-1, this));
        buttons.put(0, none);
        buttons.put(1, none);
        buttons.put(2, none);
        buttons.put(3, none);
        buttons.put(4, none);
        buttons.put(5, none);
        buttons.put(6, none);
        buttons.put(7, none);
        buttons.put(8, none);
        buttons.put(17, none);
        buttons.put(26, none);
        buttons.put(35, none);
        buttons.put(44, none);
        buttons.put(52, none);
        buttons.put(51, none);
        buttons.put(50, none);
        buttons.put(48, none);
        buttons.put(47, none);
        buttons.put(46, none);
        buttons.put(36, none);
        buttons.put(27, none);
        buttons.put(18, none);
        buttons.put(9, none);
        return buttons;
    }

    @AllArgsConstructor
    private static class ItemButton extends Button {

        private final BidInfo bids;

        @Override
        public ItemStack getButtonItem(Player player) {
            val item = bids.getItemStack().clone();
            val meta = item.getItemMeta();
            val first = Auction.getSortedBids().get(bids);
            if (first != null) {
                meta.setLore(CC.translate(Arrays.asList(
                        "&6Start&7: &f" + bids.getStartBid(),
                        "&6Amount&7: &f" + bids.getBidAmount(),
                        "",
                        "&6Bids&7: &f" + BidInfo.getInfo().get(bids).size(),
                        "",
                        "&6Top&7: &f" + first.getPlayer().getName() + " (" + first.getMoney() + ")")));
            } else {
                meta.setLore(CC.translate(Arrays.asList(
                        "&6Start&7: &f" + bids.getStartBid(),
                        "&6Amount&7: &f" + bids.getBidAmount())));
            }
            item.setItemMeta(meta);
            return item;
        }

        @Override
        public void clicked(Player player, int slot, ClickType clickType, int hotbarButton) {
            if (!Auction.get().isEnableAuction()) return;
            if (BidPlayers.getInformation().containsKey(player.getUniqueId()) && BidPlayers.getInformation().get(player.getUniqueId()).getBidInfo() == bids) { //Este es el problema
                val bidPlayers = BidPlayers.getInformation().get(player.getUniqueId());
                val add = bidPlayers.getMoney() + bids.getBidAmount();
                if (add > Auction.get().getBalance().getBalance(player)) {
                    player.sendMessage(CC.translate("&cYou do not have enough money to continue bidding"));
                } else {
                    bidPlayers.setMoney(add);
                    player.sendMessage(CC.translate("&aYou just bid with " + add + " money"));
                }
            } else {
                if (bids.getBidAmount() > Auction.get().getBalance().getBalance(player)) {
                    player.sendMessage(CC.translate("&cYou do not have enough money to continue bidding"));
                    return;
                }
                val bidPlayers = new BidPlayers(player, bids.getBidAmount(), bids);
                BidPlayers.getInformation().put(player.getUniqueId(), bidPlayers);
                Auction.getAllBids().put(bids, bidPlayers);
                if (BidInfo.getInfo().get(bids).isEmpty()) {
                    BidInfo.getInfo().put(bids, Collections.singletonList(bidPlayers));
                } else {
                    List<BidPlayers> playersList = new ArrayList<>(BidInfo.getInfo().get(bids));
                    playersList.add(bidPlayers);
                    BidInfo.getInfo().put(bids, playersList);
                }
                player.sendMessage(CC.translate("&aYou started your bid with " + bids.getBidAmount()));
            }
        }
    }

    private static class TimeLeftButton extends Button {

        @Override
        public ItemStack getButtonItem(Player player) {
            if (Auction.get().isEnableAuction()) {
                return new ItemBuilder(Material.BOOK)
                        .name("&cTimeLeft&7: &f" + TimeUtil.millisToTimer(Auction.get().getCooldown().getRemaining()))
                        .build();
            } else {
                return new ItemBuilder(Material.BOOK)
                        .build();
            }
        }
    }
}
