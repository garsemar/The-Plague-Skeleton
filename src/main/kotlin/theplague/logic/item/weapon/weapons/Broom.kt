package theplague.logic.item.weapon.weapons

import theplague.logic.item.weapon.*
import theplague.interfaces.Iconizable

class Broom(timesLeft: Int) : Weapon(timesLeft) {
    override val icon : String = "🧹";
}
