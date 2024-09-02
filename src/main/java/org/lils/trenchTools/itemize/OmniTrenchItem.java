package org.lils.trenchTools.itemize;

import co.crystaldev.alpinecore.framework.config.object.item.DefinedConfigItem;
import co.crystaldev.alpinecore.util.Messaging;
import co.crystaldev.itemize.api.ItemType;
import co.crystaldev.itemize.api.ItemizeItem;
import com.cryptomorin.xseries.XMaterial;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.lils.trenchTools.TrenchTools;
import org.lils.trenchTools.config.ItemConfig;

import java.util.Set;

public class OmniTrenchItem implements ItemizeItem {
    private static final OmniTrenchItem INSTANCE = new OmniTrenchItem();
    public static @NotNull OmniTrenchItem get() {
        return INSTANCE;
    }

    private static final NamespacedKey KEY = NamespacedKey.fromString("trenchtools:trench_pick");

    private static final Material PICKAXE = XMaterial.NETHERITE_PICKAXE.isSupported() ?
            XMaterial.NETHERITE_PICKAXE.parseMaterial() :
            XMaterial.DIAMOND_PICKAXE.parseMaterial();

    private static final Material AXE = XMaterial.NETHERITE_AXE.isSupported() ?
            XMaterial.NETHERITE_AXE.parseMaterial() :
            XMaterial.DIAMOND_AXE.parseMaterial();

    private static final Material SHOVEL = XMaterial.NETHERITE_SHOVEL.isSupported() ?
            XMaterial.NETHERITE_SHOVEL.parseMaterial() :
            XMaterial.DIAMOND_SHOVEL.parseMaterial();

    private final DefinedConfigItem configItem;

    public OmniTrenchItem() {
        super();
        this.configItem = ItemConfig.getInstance().omniTrenchItem;
    }

    private final int[] radiusOptions = {1, 2, 3};

    private int currentIndex = 0;


    @Override
    public @NotNull ItemType getType() {
        return ItemType.ITEM_STACK;
    }

    @Override
    public @NotNull ItemStack getItem() {
        ItemStack itemStack = this.configItem.build(TrenchTools.getInstance());
        itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(KEY, PersistentDataType.BOOLEAN, true);
            meta.addEnchant(Enchantment.DIG_SPEED, 5, true);
            meta.setUnbreakable(true);
        });
        return itemStack;
    }

    @Override
    public @NotNull Set<PotionEffect> getEffects() {
        throw new UnsupportedOperationException("no potion effect type");
    }

    @Override
    public boolean matches(@NotNull ItemStack itemStack) {
        if (XMaterial.matchXMaterial(itemStack) != this.configItem.getType()
                && (XMaterial.matchXMaterial(itemStack) != XMaterial.NETHERITE_AXE
                && XMaterial.matchXMaterial(itemStack) != XMaterial.NETHERITE_SHOVEL)) {
            return false;
        }

        return itemStack.getItemMeta().getPersistentDataContainer().has(KEY);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    public void onBreak(@NotNull BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        int radius = getCurrentRadius();

        Messaging.broadcast(Component.text("Broken: " + block.getType()));

        createTrench(block, radius);
    }

    public void onShiftClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.isSneaking() && event.getAction() == Action.RIGHT_CLICK_AIR) {
            currentIndex = (currentIndex + 1) % radiusOptions.length;
        }
    }
    public int getCurrentRadius() {
        return radiusOptions[currentIndex];
    }

    public void onInteract(@NotNull PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action != Action.LEFT_CLICK_BLOCK) {
            return;
        }

        Block block = event.getClickedBlock();
        if (block == null) {
            return;
        }

        Material material = block.getType();
        Player player = event.getPlayer();
        ItemStack itemStack = this.getItem();

        if (Tag.MINEABLE_PICKAXE.isTagged(material)) {
            itemStack = itemStack.withType(PICKAXE);
        }
        else if (Tag.MINEABLE_SHOVEL.isTagged(material)) {
            itemStack = itemStack.withType(SHOVEL);
        }
        else if (Tag.MINEABLE_AXE.isTagged(material)) {
            itemStack = itemStack.withType(AXE);
        }

        player.getInventory().setItemInMainHand(itemStack);
    }

    private void createTrench(Block centerBlock, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                for(int y = -radius; y <= radius; y++) {
                    Block block = centerBlock.getRelative(x, y, z);

                    if (x == 0 && z == 0 && y == 0) {
                        continue;
                    }

                    if (block.getType() != Material.AIR && block.getType() != Material.BEDROCK) {
                        block.setType(Material.AIR);
                    }
                }
            }
        }
    }
}
