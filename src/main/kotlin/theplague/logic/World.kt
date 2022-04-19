package theplague.logic

import theplague.interfaces.*
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
                Position(x, y),
                player
            )
        }
    },
): IWorld {

    init {
        territories[width/2][height/2].hasPlayer();
    }

    override fun nextTurn() {

        val wRand = (0 until width).random();
        val hRand = (0 until height).random()
        territories[wRand][hRand].onUpdate()
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

        player.position = position;
        territories[position.y][position.x].hasPlayer();
    }

    override fun exterminate() {

    }

    override fun takeableItem(): Iconizable? {
        return null
    }

    override fun takeItem() {

    }
}