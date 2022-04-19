package theplague.logic

import theplague.interfaces.IPlayer
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.vehicles.OnFoot
import theplague.logic.item.weapon.weapons.Hand

class Player(val position: Position) : IPlayer, Iconizable {
    override val icon : String = "👆";
    override val turns: Int = 10
    override val livesLeft: Int = 15
    override val currentWeapon: Iconizable = Hand(1)
    override val currentVehicle: Iconizable = OnFoot(1)

}