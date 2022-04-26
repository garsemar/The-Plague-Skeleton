package theplague.logic

import theplague.interfaces.IPlayer
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.vehicle.Vehicle
import theplague.logic.item.vehicle.vehicles.OnFoot
import theplague.logic.item.weapon.Weapon
import theplague.logic.item.weapon.weapons.Hand

class Player(var position: Position) : IPlayer, Iconizable {
    override val icon : String = "\uD83D\uDEB6";
    override var turns: Int = 0
    override val livesLeft: Int = 15
    override var currentWeapon: Weapon = Hand(1)
    override var currentVehicle: Vehicle = OnFoot(1)
}