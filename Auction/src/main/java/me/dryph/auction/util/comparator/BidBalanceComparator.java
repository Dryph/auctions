package me.tulio.auction.util.comparator;

import lombok.AllArgsConstructor;
import me.tulio.auction.entry.BidInfo;
import me.tulio.auction.entry.BidPlayers;

import java.util.Comparator;
import java.util.Map;

@AllArgsConstructor
public class BidBalanceComparator implements Comparator<Map.Entry<BidInfo, BidPlayers>> {

    @Override
    public int compare(Map.Entry<BidInfo, BidPlayers> o1, Map.Entry<BidInfo, BidPlayers> o2) {
        return Integer.compare(o1.getValue().getMoney(), o2.getValue().getMoney());
    }
}
