package theplague.logic

import theplague.interfaces.ITerritory
import theplague.interfaces.Iconizable
import theplague.interfaces.Position

class Colony(override val icon : String) : Iconizable {

}

class Territory(val position: Position) : ITerritory {
    val plagueSize: Int = 10

    override fun iconList() : List<Iconizable> {
        return listOf(Colony("\uD83D\uDC1C"), Colony("\uD83D\uDC09"))
    }


    fun exterminate() {

    }
}