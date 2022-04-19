package theplague.logic.item.vehicle.vehicles

import theplague.interfaces.Iconizable
import theplague.logic.item.vehicle.Vehicle

class Bicycle(timesLeft: Int): Vehicle(timesLeft) {
    override val icon = "🚲"
}