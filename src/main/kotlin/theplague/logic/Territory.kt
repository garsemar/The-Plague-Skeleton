package theplague.logic

import theplague.interfaces.ITerritory
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.vehicle.vehicles.OnFoot
import theplague.logic.item.weapon.Weapon
import theplague.logic.item.vehicle.Vehicle
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

class Territory(val position: Position, val player: Player) : ITerritory {
    val plagueSize: Int = 3

    private var hasPlayer : Boolean = false;

    private val territoryItems : MutableList<Iconizable> = mutableListOf();

    override fun iconList() : List<Iconizable> {
        val myIconList: MutableList<Iconizable> = territoryItems
        if (hasPlayer) {
            hasPlayer = false
            myIconList.add(player)
        }

        return myIconList
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

    fun getRandomPlague() : Iconizable? {
        var random = (0 until 100).random();
        return when (random) {
            in 0 .. 29 -> Ant(1, 30.0)
            in 30 .. 40 -> Dragon(1, 5)
            else -> {
                null
            }
        }
    }

    fun hasPlayer() {
        hasPlayer = true;
    }

    fun onUpdate() {
        territoryItems.clear()
        val newItem = getRandomItem()
        if(newItem != null) {
            territoryItems.add(newItem)
        }

        val newPlague = getRandomPlague()
        if(newPlague != null) {
            territoryItems.add(newPlague)
        }
        if (newPlague != null) {
            println(newPlague.icon)
        }
        if (newItem != null) {
            println(newItem.icon)
        }
    }
    fun exterminate() {

    }
}