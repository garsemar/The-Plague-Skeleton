package theplague.logic

import theplague.interfaces.ITerritory
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.vehicles.OnFoot

sealed class Colony() : Iconizable {

}

class Ant() : Colony() {
    override val icon : String = "\uD83D\uDC1C"
}

class Dragon() : Colony() {
    override val icon : String = "\uD83D\uDC09";
}

class Territory(val position: Position, val player: Player) : ITerritory {
    val plagueSize: Int = 3

    private var hasPlayer : Boolean = false;

    private val territoryIcons : MutableList<Iconizable> = mutableListOf();

    override fun iconList() : List<Iconizable> {
        return territoryIcons;
    }

    fun addItem() {
    }

    fun generatePlague() {
        
    }

    fun hasPlayer() {
        hasPlayer = true;
    }

    fun onUpdate() {
        territoryIcons.clear();

        if(hasPlayer) {
           territoryIcons.add(player)
        }
    }
    fun exterminate() {

    }
}