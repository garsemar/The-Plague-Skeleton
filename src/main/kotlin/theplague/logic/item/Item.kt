package theplague.logic.item

import theplague.interfaces.Iconizable

abstract class Item(var timesLeft: Int) : Iconizable {
    fun use() {
        timesLeft--;
    }
}