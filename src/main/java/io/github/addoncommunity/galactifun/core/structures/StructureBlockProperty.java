package io.github.addoncommunity.galactifun.core.structures;

import lombok.AllArgsConstructor;
import org.bukkit.block.data.BlockData;

import java.util.function.BiConsumer;

@AllArgsConstructor
final class StructureBlockProperty<D extends BlockData, P> {

    private final P value;
    private final BiConsumer<D, P> paste;
    
    @SuppressWarnings("unchecked")
    void paste(BlockData data) {
        this.paste.accept((D) data, this.value);
    }
    
}
