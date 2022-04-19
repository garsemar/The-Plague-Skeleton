package theplague.logic

import theplague.interfaces.IPlayer
import theplague.interfaces.Iconizable

class Player() : IPlayer {
    override val turns: Int = 10
    override val livesLeft: Int = 15
    override val currentWeapon: Iconizable =
        override val currentVehicle: Iconizable =
}