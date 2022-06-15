package me.tulio.auction.util.menu.buttons;

import lombok.AllArgsConstructor;
import me.tulio.auction.util.ItemBuilder;
import me.tulio.auction.util.menu.Button;
import me.tulio.auction.util.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class BackButton extends Button {

    private final Menu back;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.BED)
                .name("&cGo back")
                .build();
    }

    @Override
    public void clicked(Player player, int i, ClickType clickType, int hb) {
        Button.playNeutral(player);
        this.back.openMenu(player);
    }
}
