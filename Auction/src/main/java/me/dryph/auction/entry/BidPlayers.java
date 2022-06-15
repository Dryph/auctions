package me.tulio.auction.entry;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor
public class BidPlayers {

    @Getter public static Map<UUID, BidPlayers> information = Maps.newHashMap();

    public Player player;
    public int money;
    public BidInfo bidInfo;
}
