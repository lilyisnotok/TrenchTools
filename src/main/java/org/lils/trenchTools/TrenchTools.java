package org.lils.trenchTools;

import co.crystaldev.alpinecore.AlpinePlugin;
import co.crystaldev.itemize.api.Identifier;
import co.crystaldev.itemize.api.Itemize;
import lombok.Getter;
import org.lils.trenchTools.itemize.OmniTrenchItem;

public class TrenchTools extends AlpinePlugin {
    @Getter
    private static TrenchTools instance;{ instance = this; }

    public void onStart() {
        Itemize itemize = Itemize.get();

        itemize.register(Identifier.fromString("trenchtools:omni_trench_tool"), OmniTrenchItem.get());
    }
}
