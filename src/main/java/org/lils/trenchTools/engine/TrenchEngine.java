package org.lils.trenchTools.engine;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.alpinecore.framework.engine.AlpineEngine;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lils.trenchTools.itemize.OmniTrenchItem;

public class TrenchEngine extends AlpineEngine {
    protected TrenchEngine(@NotNull AlpinePlugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();

        if(OmniTrenchItem.get().matches(itemInHand)) {
            OmniTrenchItem.get().onBreak(event);
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();

        if(OmniTrenchItem.get().matches(itemInHand)) {
            OmniTrenchItem.get().onInteract(event);
        }

    }

    @EventHandler
    public void onShiftClick(PlayerInteractEvent event) {
        ItemStack itemInHand = event.getPlayer().getItemInHand();

        if(OmniTrenchItem.get().matches(itemInHand)) {
            OmniTrenchItem.get().onShiftClick(event);
        }

    }
}
