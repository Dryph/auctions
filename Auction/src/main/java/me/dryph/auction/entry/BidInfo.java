package me.tulio.auction.entry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.tulio.auction.Auction;
import me.tulio.auction.util.FileConfig;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@Getter @Setter
public class BidInfo {

    @Getter public static Map<BidInfo, List<BidPlayers>> info = Maps.newHashMap();

    public final String identifier;
    public final ItemStack itemStack;
    public int startBid, bidAmount, position;

    public BidInfo(String identifier, ItemStack itemStack) {
        this.identifier = identifier;
        this.itemStack = itemStack;

        info.put(this, Lists.newArrayList());
    }

    public BidInfo(String identifier, ItemStack itemStack, int startBid, int bidAmount, int position) {
        this.identifier = identifier;
        this.itemStack = itemStack;
        this.startBid = startBid;
        this.bidAmount = bidAmount;
        this.position = position;

        info.put(this, Lists.newArrayList());
    }

    public void save() {
        FileConfig config = Auction.get().getAuctionConfig();

        config.getConfiguration().set("auctions." + identifier + ".ItemStack", itemStack);
        config.getConfiguration().set("auctions." + identifier + ".position", position);
        config.getConfiguration().set("auctions." + identifier + ".StartBid", startBid);
        config.getConfiguration().set("auctions." + identifier + ".BidAmount", bidAmount);

        config.save();
        config.reload();
    }

}
