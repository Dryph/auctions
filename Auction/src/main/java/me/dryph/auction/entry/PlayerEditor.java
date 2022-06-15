package me.tulio.auction.entry;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.tulio.auction.Auction;
import org.bukkit.entity.Player;

import java.util.Map;

@Getter @Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PlayerEditor {

    @Getter public static Map<Player, PlayerEditor> editors = Maps.newHashMap();

    public final Player player;
    public boolean startBid, amountBid;
    public int position = Auction.getBids().size();

    public static PlayerEditor get(Player player) {
        return editors.get(player);
    }

    public static void remove(Player player) {
        editors.remove(player);
    }
}
