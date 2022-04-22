package theplague.logic

import theplague.interfaces.ITerritory
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.weapon.Weapon
import theplague.logic.item.weapon.weapons.*
import theplague.logic.item.*

class Colonization(colony: Colony, position: Position)

sealed class Colony(size: Int) : Iconizable {
    fun willReproduce(): Boolean{
        return false
    }
    fun reproduce(){

    }
    fun needsToExpand(){

    }
    fun attacked(weapon: Weapon){

    }
    fun colonizedBy(plague: Colony): Colony {
        return plague
    }
    fun expand(position: Position, maxPosition: Position): List<Colonization>{
        return listOf()
    }
}

class Ant(size: Int, reproductioTax: Double) : Colony(size) {
    override val icon : String = "\uD83D\uDC1C"
}

class Dragon(size: Int, val timeToReproduce: Int) : Colony(size) {
    override val icon : String = "\uD83D\uDC09"
}

class Empty() : Iconizable {
    override val icon: String = "";
}

enum class IcoSlots(val index: Int){
    PLAYER(0),
    ITEM(0),
    COLONY(0),
}

class Territory(val position: Position) : ITerritory {

    // Status
    private var hasPlayer : Boolean = false;
    private var isColonized : Boolean = false; val plagueSize: Int = 3;

    // Contains
    private var plague : Colony? = null;
    var item : Item? = null;
    private var player: Player? = null;

    override fun iconList() : List<Iconizable> {
        val icoList : MutableList<Iconizable> = mutableListOf()

        if(plague != null) {
            repeat(plagueSize) {
                icoList.add(plague!!)
            }
        }
        if(item != null) {
            icoList.add(item!!)
        }
        if(player != null) {
            icoList.add(player!!)
        }

        return icoList
    }

    private fun getRandomItem() : Item? {
        when ((0 until 100).random()) {
            in 0 .. 24 -> return Bicycle(5)
            in 25 .. 34 -> return Helicopter(5)
            in 35 .. 59 -> return Broom(1)
            in 60 .. 69 -> return Sword(0)
            in 70 .. 99 -> return null
        }
        return null
    }

    private fun getRandomPlague() : Colony? {
        var random = (0 until 100).random();
        return when (random) {
            in 0 .. 29 -> Ant(1, 30.0)
            in 30 .. 40 -> Dragon(1, 5)
            else -> {
                null
            }
        }
    }

    fun hasNotPlayer() {
        this.player = null;
    }

    fun hasPlayer(player: Player) {
        this.player = player;
    }

    fun spawnPlague() {
        if(plague == null)
            plague = getRandomPlague();
    }

    fun removeItem() {
        item = null
    }
    fun spawnItem() {
        val newItem = getRandomItem();
        if(newItem != null)
            item = newItem
    }


    fun exterminate() {

    }
}