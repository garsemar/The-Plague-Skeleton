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

sealed class Colony() : Iconizable {
    var plagueSize: Int = 1;
    var maxPlagueSize: Int = 3;
    open fun willReproduce(): Boolean{
        return false
    }
    
    fun reproduce(){
        if(plagueSize >= maxPlagueSize)
            needsToExpand()
        else
            plagueSize++;

    }
    fun needsToExpand(){

    }
    open fun attacked(weapon: Weapon){
    }
    fun colonizedBy(plague: Colony): Colony {
        return plague
    }
    open fun expand(position: Position, maxPosition: Position): List<Colonization>{
        return listOf()
    }
}

class Ant() : Colony() {
    override val icon : String = "\uD83D\uDC1C"

    var reproductionTax: Double = 30.0;

    override fun willReproduce() : Boolean {
        if((0 until 100).random() in 0 until reproductionTax.toInt()) {
            return true
        }
        return false;
    }

    override fun attacked(weapon: Weapon){
        when(weapon){
            is Hand -> plagueSize -= 2
            is Broom -> plagueSize -= 3
            is Sword -> plagueSize -= 1
        }
        if (plagueSize < 0) {
            plagueSize = 0
        }
    }

    override fun expand(position: Position, maxPosition: Position): List<Colonization>{
        if()
    }
}

class Dragon() : Colony() {
    override val icon : String = "\uD83D\uDC09"
    private val timeToReproduce: Int = 5;
    var lastTurn = 0;
    var turn: Int = 0;
    override fun willReproduce() : Boolean {
        if(turn - lastTurn >= timeToReproduce) {
            lastTurn = turn;
            return true
        }
        return false;
    }
    override fun attacked(weapon: Weapon){
        when(weapon){
            is Hand -> plagueSize -= 0
            is Broom -> plagueSize -= 0
            is Sword -> plagueSize -= 1
        }
        if (plagueSize < 0) {
            plagueSize = 0
        }
    }
}

class Empty() : Iconizable {
    override val icon: String = "";
}

class Territory(val position: Position) : ITerritory {

    // Status
    private var hasPlayer : Boolean = false;
    private var isColonized : Boolean = false; val plagueSize: Int = 1;

    // Contains
    private var plague : Colony? = null;
    var item : Item? = null;
    private var player: Player? = null;

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
    
    fun reproducePlague(turn: Int) {
        when(plague) {
            is Ant ->
            {
                val ants = (plague as Ant);
                if(ants.willReproduce())
                    ants.reproduce()

            }
            is Dragon -> {
                val dragons = (plague as Dragon);
                dragons.turn = turn
                if(dragons.willReproduce())
                    dragons.reproduce()

            }
            else -> {}
        }
    }
    
    private fun getRandomPlague() : Colony? {
        return when ((0 until 100).random()) {
            in 0 .. 29 -> Ant()
            in 30 .. 40 -> Dragon()
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

    fun spawnPlague(turn: Int) {
        if(plague == null)
            plague = getRandomPlague();
            if(plague is Dragon)
                (plague as Dragon).lastTurn = turn;
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