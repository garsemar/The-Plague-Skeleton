package theplague.logic

import theplague.interfaces.ITerritory
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.enemies.Ant
import theplague.logic.enemies.Colony
import theplague.logic.enemies.Dragon
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.weapon.weapons.*
import theplague.logic.item.*


class Territory(val position: Position) : ITerritory {

    // Status
    private var isColonized : Boolean = false; val plagueSize: Int = 1;

    // Contains
    private var plague : Colony? = null;
    var item : Item? = null;
    private var player: Player? = null;
    var turns : Int = 0;

    override fun iconList() : List<Iconizable> {
        val icoList : MutableList<Iconizable> = mutableListOf()

        if(plague != null) {
            repeat(plague!!.plagueSize) {
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

    fun reproducePlague(maxPosition: Position) {
        when(plague) {
            is Ant ->
            {
                val ants = (plague as Ant);
                if(ants.needsToExpand())
                    ants.expand(position, maxPosition)
                else
                    if(ants.willReproduce())
                        ants.reproduce()
            }
            is Dragon -> {
                val dragons = (plague as Dragon);
                if(dragons.willReproduce())
                    dragons.reproduce()
            }
            else -> {}
        }
    }

    private fun getRandomPlague() : Colony? {
        return when ((0 until 100).random()) {
            in 1 .. 30 -> Ant()
            in 30 .. 40 -> Dragon()
            else -> {
                null
            }
        }
    }

    fun removePlayer() {
        this.player = null;
    }

    fun hasPlayer(player: Player) {
        this.player = player;
    }

    fun spawnPlague(plague: Colony? = null) {
        if(this.plague == null)
            if(plague == null)
                this.plague = getRandomPlague();
            else
                this.plague = plague
    }

    fun removeItem() {
        item = null
    }
    fun spawnItem() {
        val newItem = getRandomItem();
        if(newItem != null)
            item = newItem
    }

    fun attackPlague(){
        //plague?.attacked()
        player?.let { plague?.attacked(it.currentWeapon) }
        if(plague?.plagueSize == 0){
            plague = null
        }
    }

    fun exterminate() {

    }
}