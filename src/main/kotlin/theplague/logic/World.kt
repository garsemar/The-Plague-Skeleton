package theplague.logic

import theplague.interfaces.*
import theplague.logic.item.vehicle.Vehicle
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.vehicle.vehicles.OnFoot
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

    var takeableItem : Iconizable? = null;

    init {
        territories[width/2][height/2].hasPlayer(player);
    }

    fun getRandomWorldPosition() : Position {
        return Position((0 until width).random(), (0 until height).random())
    }

    override fun nextTurn() {

        // Spawn new colony
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].spawnPlague()

        // Spawn new item
        val itemPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].spawnItem()

    }

    override fun gameFinished(): Boolean {
        return false
    }

    override fun canMoveTo(position: Position) : Boolean{

        val pos1 = player.position;
        val pos2 = position;
        val distance = sqrt((pos2.y - pos1.y).toDouble().pow(2) + (pos2.x - pos1.x).toDouble().pow(2))

        when (player.currentVehicle::class) {
            OnFoot::class -> return distance < 1.5
            Bicycle::class -> return distance < 4.5
            Helicopter::class -> return true
        }
        return true
    }

    override fun moveTo(position: Position) {
        val playerPos = player.position;
        territories[playerPos.y][playerPos.x].hasNotPlayer()

        player.position = position;
        val targetTerritory = territories[position.y][position.x];
        targetTerritory.hasPlayer(player)
        takeableItem = targetTerritory.item
        println(targetTerritory.iconList())
        println(targetTerritory.item)

    }
    override fun exterminate() {

    }

    override fun takeableItem(): Iconizable? {
        return takeableItem
    }

    override fun takeItem() {
        p
    }
}