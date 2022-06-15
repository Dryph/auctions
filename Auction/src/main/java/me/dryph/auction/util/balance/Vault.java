package me.tulio.auction.util.balance;

import me.tulio.auction.Auction;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;

public class Vault implements Balance {

    @Override
    public double getBalance(Player player) {
        return Auction.get().getEcon().getBalance(player);
    }

    @Override
    public void setBalance(Player player, int amount) {
        EconomyResponse response = Auction.get().getEcon().depositPlayer(player, amount);
        Auction.get().getEcon().format(response.amount);
    }

    @Override
    public void giveBalance(Player player, int amount) {
        EconomyResponse response = Auction.get().getEcon().depositPlayer(player, getBalance(player) + amount);
        Auction.get().getEcon().format(response.amount);
    }

    @Override
    public void removeBalance(Player player, int amount) {
        EconomyResponse response = Auction.get().getEcon().withdrawPlayer(player, amount);
        Auction.get().getEcon().format(response.amount);
    }
}
