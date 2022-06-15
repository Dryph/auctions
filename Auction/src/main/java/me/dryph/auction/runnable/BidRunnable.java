package me.tulio.auction.runnable;

import me.tulio.auction.Auction;
import me.tulio.auction.EndBidEvent;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class BidRunnable extends BukkitRunnable {

    @Override
    public void run() {
        if (Auction.get().getCooldown().hasExpired()) {
            Bukkit.getServer().getPluginManager().callEvent(new EndBidEvent());
            cancel();
        }
    }
}
