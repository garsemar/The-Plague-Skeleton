package theplague.logic

import theplague.interfaces.*
import theplague.logic.item.Item
import theplague.logic.item.vehicle.Vehicle
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.vehicle.vehicles.OnFoot
import theplague.logic.item.weapon.Weapon
import kotlin.math.*

class World(
    override val width: Int,
    override val height: Int,
    override val player: Player = Player(Position(width / 2, height / 2)),
    override val territories: List<List<Territory>> = List(height) { x ->
        List<Territory>(width) { y ->
            Territory(
                Position(x, y)
            )
        }
    },
): IWorld {

    var territoryTakeableItem : Iconizable? = null;
    init {
        territories[width/2][height/2].hasPlayer(player);
        // Spawn new colony
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].spawnPlague(Ant())
    }

    private fun getRandomWorldPosition() : Position {
        return Position((0 until width).random(), (0 until height).random())
    }

    override fun nextTurn() {
        player.turns++;

        // Spawn new colony
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].spawnPlague()

        // Spawn new item
        val itemPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].spawnItem()

        // Reproduce plague
        territories.flatten().forEach { if(it.position.x != player.position.x && it.position.x != player.position.y) it.reproducePlague(Position(width, height)) }

        territories.flatten().forEach {
            if(Ant().needsToExpand()){
                println("hola2")
                Ant().expand(it.position, Position(width, height))
            }
        }
    }

    override fun gameFinished(): Boolean {
        return false
    }

    override fun canMoveTo(targetPos: Position) : Boolean{

        val distance = sqrt((targetPos.y - player.position.y).toDouble().pow(2) + (targetPos.x - player.position.x).toDouble().pow(2))

        when (player.currentVehicle::class) {
            OnFoot::class -> return distance < 1.5
            Bicycle::class -> return distance < 4.5
            Helicopter::class -> return true
        }
        return true
    }

    private fun movePlayerToTerritory(position: Position) {
        player.position = position;
    }

    override fun moveTo(nextPosition: Position) {
        // Remove player from territory
        territories[player.position.y][player.position.x].removePlayer()

        // Move player
        this.movePlayerToTerritory(nextPosition)

        val targetTerritory = territories[nextPosition.y][nextPosition.x]
        targetTerritory.hasPlayer(player)
        territoryTakeableItem = targetTerritory.item
        targetTerritory.removeItem()
        targetTerritory.attackPlague()
    }
    override fun exterminate() {

    }

    override fun takeableItem(): Iconizable? {
        return territoryTakeableItem
    }

    override fun takeItem() {
        if(territoryTakeableItem != null) {

            when(territoryTakeableItem) {
                is Vehicle -> player.currentVehicle = territoryTakeableItem as Vehicle
                is Weapon -> player.currentWeapon = territoryTakeableItem as Weapon
            }
        }
    }
}