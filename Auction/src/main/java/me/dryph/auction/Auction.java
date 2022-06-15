package me.tulio.auction;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import lombok.val;
import me.tulio.auction.command.AuctionCommand;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.entry.BidPlayers;
import me.tulio.auction.entry.PlayerEditor;
import me.tulio.auction.listener.AuctionListener;
import me.tulio.auction.util.Cooldown;
import me.tulio.auction.util.FileConfig;
import me.tulio.auction.util.TaskUtil;
import me.tulio.auction.util.balance.Balance;
import me.tulio.auction.util.balance.Vault;
import me.tulio.auction.util.command.CommandFramework;
import me.tulio.auction.util.comparator.BidBalanceComparator;
import me.tulio.auction.util.menu.ButtonListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.stream.Collectors;

@Getter @Setter
public class Auction extends JavaPlugin {

    @Getter public static List<BidInfo> bids = Lists.newArrayList();
    @Getter public static Map<PlayerEditor, BidInfo> editor = Maps.newHashMap();
    @Getter public static Map<BidInfo, BidPlayers> allBids = Maps.newHashMap();
    @Getter public static Map<BidInfo, BidPlayers> sortedBids = Maps.newHashMap();
    private CommandFramework commandFramework;
    private boolean enableAuction = false;
    private FileConfig dataConfig, auctionConfig;
    private Economy econ;
    private Balance balance;
    private Cooldown cooldown;

    @Override
    public void onEnable() {
        super.onEnable();
        loadConfigs();
        loadManagers();
        loadListeners();
        loadCommands();
        setupEconomy();
        init();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadManagers() {
        this.commandFramework = new CommandFramework(this);
        this.cooldown = new Cooldown(0);
    }

    private void loadCommands() {
        new AuctionCommand();
    }

    private void loadListeners() {
        new AuctionListener();
        new ButtonListener(this);
    }

    private void loadConfigs() {
        this.dataConfig = new FileConfig(this, "data.yml");
        this.auctionConfig = new FileConfig(this, "auction.yml");
    }

    private void setupEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null) econ = rsp.getProvider();
        this.balance = new Vault();
    }

    private void init() {
        for (String auctions : getAuctionConfig().getConfiguration().getConfigurationSection("auctions").getKeys(false)) {
            val itemStack = getAuctionConfig().getConfiguration().getItemStack("auctions." + auctions + ".ItemStack");
            val position = getAuctionConfig().getInt("auctions." + auctions + ".position");
            val startBid = getAuctionConfig().getInt("auctions." + auctions + ".StartBid");
            val bidAmount = getAuctionConfig().getInt("auctions." + auctions + ".BidAmount");
            Auction.getBids().add(new BidInfo(auctions, itemStack, position, startBid, bidAmount));
        }

        TaskUtil.runTimerAsync(() -> {
            if (isEnableAuction()) {
                sortedBids = getAllBids().entrySet()
                        .stream()
                        .sorted(new BidBalanceComparator().reversed())
                        .limit(100)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
            }
        }, 20L, 20L);
    }

    public static Auction get() {
        return getPlugin(Auction.class);
    }
}
