package io.github.addoncommunity.galactifun.util.items

import io.github.bakedlibs.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils
import it.unimi.dsi.fastutil.ints.IntArrayList
import it.unimi.dsi.fastutil.ints.IntList
import it.unimi.dsi.fastutil.ints.IntOpenHashSet
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow
import org.bukkit.block.Block
import org.bukkit.entity.Player


class MenuBuilder {

    private val inputBorder = IntArrayList()
    private val outputBorder = IntArrayList()
    private val inputs = IntArrayList()
    private val outputs = IntArrayList()

    var inputBorderItem = CustomItemStack(ChestMenuUtils.getInputSlotTexture(), "&9Input")
    var outputBorderItem = CustomItemStack(ChestMenuUtils.getOutputSlotTexture(), "&6Output")

    var numRows = 6
        set(value) {
            field = value.coerceIn(1, 6)
        }

    fun inputBorder(vararg points: Int) = inputBorder.addAll(points)

    fun outputBorder(vararg points: Int) = outputBorder.addAll(points)

    fun input(vararg points: Int): MaybeBorder {
        inputs.addAll(points)
        return MaybeBorder(points, inputBorder)
    }

    fun output(vararg points: Int): MaybeBorder {
        outputs.addAll(points)
        return MaybeBorder(points, outputBorder)
    }

    inner class MaybeBorder internal constructor(private val points: IntArray, private val borderList: IntList) {
        fun addBorder() {
            var i = 0
            while (i < points.size) {
                val cx = points[i]
                val cy = points[i + 1]
                for (x in border) {
                    for (y in border) {
                        val result = coordsToSlot(cx + x, cy + y)
                        if (result !in inputs && result !in outputs && result in 0 until numRows * 9) {
                            borderList.add(result)
                        }
                    }
                }
                i += 2
            }
        }
    }

    fun applyOn(item: SlimefunItem) {
        object : BlockMenuPreset(item.id, item.itemName) {
            private val lastIndex = numRows * 9
            private val ins = inputs.toIntArray()
            private val outs = outputs.toIntArray()

            override fun init() {
                val bg = IntOpenHashSet(IntArray(lastIndex) { it })
                for (i in 0 until inputBorder.size) {
                    val index = inputBorder.getInt(i)
                    bg.remove(index)
                    this.addItem(index, inputBorderItem, ChestMenuUtils.getEmptyClickHandler())
                }
                for (i in 0 until outputBorder.size) {
                    val index = outputBorder.getInt(i)
                    bg.remove(index)
                    this.addItem(index, outputBorderItem, ChestMenuUtils.getEmptyClickHandler())
                }
                ins.forEach(bg::remove)
                outs.forEach(bg::remove)
                val bgIt = bg.iterator()
                while (bgIt.hasNext()) {
                    this.addItem(bgIt.nextInt(), ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler())
                }
                this.isEmptySlotsClickable = true
            }

            override fun canOpen(b: Block, p: Player): Boolean {
                return if (p.hasPermission("slimefun.inventory.bypass")) true
                else item.canUse(p, false) && Slimefun.getProtectionManager()
                    .hasPermission(p, b, Interaction.INTERACT_BLOCK)
            }

            override fun getSlotsAccessedByItemTransport(flow: ItemTransportFlow): IntArray {
                return when (flow) {
                    ItemTransportFlow.INSERT -> ins
                    ItemTransportFlow.WITHDRAW -> outs
                }
            }
        }
    }
}

private fun coordsToSlot(x: Int, y: Int): Int {
    return x + y * 9
}

private fun IntList.addAll(array: IntArray) {
    array.forEach(this::add)
}

private val border = -1..1

inline fun buildMenu(block: MenuBuilder.() -> Unit): MenuBuilder {
    return MenuBuilder().apply(block)
}