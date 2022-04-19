package theplague.logic.item.weapon.weapons

import theplague.interfaces.Iconizable
import theplague.logic.item.weapon.*

class Hand(timesLeft: Int) : Weapon(timesLeft) {
    override val icon : String = "👆"
}
