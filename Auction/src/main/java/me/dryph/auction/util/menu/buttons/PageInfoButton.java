package me.tulio.auction.util.menu.buttons;

import lombok.AllArgsConstructor;
import me.tulio.auction.util.ItemBuilder;
import me.tulio.auction.util.menu.Button;
import me.tulio.auction.util.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor
public class PageInfoButton extends Button {

    private final PaginatedMenu paginatedMenu;

    @Override
    public ItemStack getButtonItem(Player player) {
        return new ItemBuilder(Material.NETHER_STAR)
                .name("&ePage Info")
                .lore("&e" + paginatedMenu.getPage() + "&7/&a" + paginatedMenu.getPages(player))
                .build();
    }

    @Override
    public boolean shouldCancel(Player player, int slot, ClickType clickType) {
        return true;
    }
}
