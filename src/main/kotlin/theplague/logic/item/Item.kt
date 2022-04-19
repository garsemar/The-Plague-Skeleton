package theplague.logic.item

import theplague.interfaces.Iconizable

abstract class Item(timesLeft: Int) : Iconizable {
    fun use() {
    }
}