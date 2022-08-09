package io.github.addoncommunity.galactifun.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;

import com.google.common.collect.Iterables;
import io.github.addoncommunity.galactifun.Galactifun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.blocks.BlockPosition;
import me.mrCookieSlime.Slimefun.api.BlockStorage;

/**
 * Class that uses {@link BlockPosition}s to store boolean tags on blocks. To be used instead of {@link BlockStorage}
 */
public final class ChunkStorage {

    private static final BlockPositionsDataType DATA_TYPE = new BlockPositionsDataType();

    private ChunkStorage() {
    }

    /**
     * Gets all blocks in this chunk that have the given tag
     * @param chunk The chunk to get the blocks from
     * @param tag The tag to get the blocks with
     * @return all blocks in this chunk that have the given tag
     */
    public static Set<BlockPosition> getTagged(@Nonnull Chunk chunk, @Nonnull String tag) {
        NamespacedKey key = new NamespacedKey(Galactifun.instance(), tag);
        DATA_TYPE.currentWorld = chunk.getWorld();
        return chunk.getPersistentDataContainer().getOrDefault(key, DATA_TYPE, new HashSet<>());
    }

    /**
     * Sets the given tag on the given blocks. All blocks not in the chunk of the first block will be ignored.
     * All blocks previously tagged with the given tag will be overwritten.
     * @param positions The blocks to set the tag on
     * @param tag The tag to set
     */
    public static void setTagged(@Nonnull Collection<BlockPosition> positions, @Nonnull String tag) {
        NamespacedKey key = new NamespacedKey(Galactifun.instance(), tag);
        if (positions.isEmpty()) return;
        Set<BlockPosition> toTag = new HashSet<>(positions);
        BlockPosition first = Iterables.get(positions, 0);
        Chunk chunk = first.getChunk();
        toTag.removeIf(pos -> pos.getChunk() != chunk);
        DATA_TYPE.currentWorld = chunk.getWorld();
        chunk.getPersistentDataContainer().set(key, DATA_TYPE, toTag);
    }

    /**
     * Checks if a block is tagged
     *
     * @param pos The block to check
     * @param tag The tag to check
     *
     * @return Whether the block is tagged
     */
    public static boolean isTagged(@Nonnull BlockPosition pos, @Nonnull String tag) {
        return getTagged(pos.getChunk(), tag).contains(pos);
    }

    /**
     * Checks if a block is tagged
     *
     * @param block The block to check
     * @param tag The tag to check
     *
     * @return Whether the block is tagged
     */
    public static boolean isTagged(@Nonnull Block block, @Nonnull String tag) {
        return isTagged(new BlockPosition(block), tag);
    }

    /**
     * Tag a block with a tag
     *
     * @param pos The block to tag
     * @param tag The tag to tag the block with
     */
    public static void tag(@Nonnull BlockPosition pos, @Nonnull String tag) {
        Set<BlockPosition> tagged = getTagged(pos.getChunk(), tag);
        tagged.add(pos);
        setTagged(tagged, tag);
    }

    /**
     * Tag a block with a tag
     *
     * @param block The block to tag
     * @param tag The tag to tag the block with
     */
    public static void tag(@Nonnull Block block, @Nonnull String tag) {
        tag(new BlockPosition(block), tag);
    }

    /**
     * Untags a block from a tag
     *
     * @param pos The block to untag
     * @param tag The tag to remove the block from
     *
     * @return true if the block was untagged, false if it was not tagged
     */
    public static boolean untag(@Nonnull BlockPosition pos, @Nonnull String tag) {
        Set<BlockPosition> tagged = getTagged(pos.getChunk(), tag);
        if (tagged.remove(pos)) {
            setTagged(tagged, tag);
            return true;
        }
        return false;
    }

    /**
     * Untags a block from a tag
     *
     * @param block The block to untag
     * @param tag The tag to remove the block from
     *
     * @return true if the block was untagged, false if it was not tagged
     */
    public static boolean untag(@Nonnull Block block, @Nonnull String tag) {
        return untag(new BlockPosition(block), tag);
    }
}
