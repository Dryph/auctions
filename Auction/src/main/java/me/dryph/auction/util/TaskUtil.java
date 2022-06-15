package me.tulio.auction.util;

import me.tulio.auction.Auction;
import org.bukkit.scheduler.BukkitRunnable;

public class TaskUtil {

    public static void run(Runnable runnable) {
        Auction.get().getServer().getScheduler().runTask(Auction.get(), runnable);
    }

    public static void runAsync(Runnable runnable) {
        try {
            Auction.get().getServer().getScheduler().runTaskAsynchronously(Auction.get(), runnable);
        } catch (IllegalStateException e) {
            Auction.get().getServer().getScheduler().runTask(Auction.get(), runnable);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimer(Runnable runnable, long delay, long timer) {
        Auction.get().getServer().getScheduler().runTaskTimer(Auction.get(), runnable, delay, timer);
    }

    public static int runTimer(BukkitRunnable runnable, long delay, long timer) {
        runnable.runTaskTimer(Auction.get(), delay, timer);
        return runnable.getTaskId();
    }

    public static void runLater(Runnable runnable, long delay) {
        Auction.get().getServer().getScheduler().runTaskLater(Auction.get(), runnable, delay);
    }

    public static void runLaterAsync(Runnable runnable, long delay) {
        try {
            Auction.get().getServer().getScheduler().runTaskLaterAsynchronously(Auction.get(), runnable, delay);
        } catch (IllegalStateException e) {
            Auction.get().getServer().getScheduler().runTaskLater(Auction.get(), runnable, delay);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(Runnable runnable, long delay, long timer) {
        try {
            Auction.get().getServer().getScheduler().runTaskTimerAsynchronously(Auction.get(), runnable, delay, timer);
        } catch (IllegalStateException e) {
            Auction.get().getServer().getScheduler().runTaskTimer(Auction.get(), runnable, delay, timer);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void runTimerAsync(BukkitRunnable runnable, long delay, long timer) {
        Auction.get().getServer().getScheduler().runTaskTimerAsynchronously(Auction.get(), runnable, delay, timer);
    }

}
