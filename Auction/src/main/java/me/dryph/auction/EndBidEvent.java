package me.tulio.auction;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EndBidEvent extends Event {

    public static HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
