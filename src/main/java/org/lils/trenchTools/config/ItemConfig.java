package org.lils.trenchTools.config;

import co.crystaldev.alpinecore.framework.config.AlpineConfig;
import co.crystaldev.alpinecore.framework.config.object.item.DefinedConfigItem;
import com.cryptomorin.xseries.XMaterial;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ItemConfig extends AlpineConfig {

    @Getter
    private static ItemConfig instance;
    { instance = this;}

    public DefinedConfigItem omniTrenchItem = DefinedConfigItem.builder(XMaterial.NETHERITE_PICKAXE)
            .name("<gold><b>OmniTool")
            .lore(
                    "Nothing here yet"
            )
            .build();

    public List<String> invalidBlocks = Arrays.asList("minecraft:bedrock");
}
