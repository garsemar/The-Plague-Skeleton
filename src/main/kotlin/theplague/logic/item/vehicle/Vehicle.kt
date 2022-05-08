package theplague.logic.item.vehicle

import theplague.interfaces.Position
import theplague.logic.item.Item
import theplague.logic.item.vehicle.vehicles.Bicycle
import theplague.logic.item.vehicle.vehicles.Helicopter
import theplague.logic.item.vehicle.vehicles.OnFoot
import kotlin.math.pow
import kotlin.math.sqrt

abstract class Vehicle(timesLeft: Int): Item(timesLeft) {
    open fun canMove(from: Position, to: Position): Boolean {
        val distance = sqrt((to.y - from.y).toDouble().pow(2) + (to.x - from.x).toDouble().pow(2))

        when (this::class) {
            OnFoot::class -> return distance < 1.5
            Bicycle::class -> return distance < 4.5
            Helicopter::class -> return true
        }
        return false
    }
}
