package theplague.logic.item.vehicle.vehicles

import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.Vehicle
import kotlin.math.pow
import kotlin.math.sqrt

class Helicopter(timesLeft: Int): Vehicle(timesLeft) {
    override val icon = "🚁"
}