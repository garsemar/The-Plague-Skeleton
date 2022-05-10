package theplague.logic.enemies

import theplague.interfaces.Position
import theplague.logic.item.weapon.Weapon
import theplague.logic.item.weapon.weapons.Broom
import theplague.logic.item.weapon.weapons.Hand
import theplague.logic.item.weapon.weapons.Sword

class Dragon() : Colony() {
    override val icon : String = "\uD83D\uDC09"
    private val timeToReproduce: Int = 5;
    var reproduceCounter = 0;
    override fun willReproduce() : Boolean {
        if(reproduceCounter >= timeToReproduce) {
            reproduceCounter = 0;
            return true
        }
        reproduceCounter++;
        return false;
    }

    override fun attacked(weapon: Weapon){
        when(weapon){
            is Hand -> plagueSize -= 0
            is Broom -> plagueSize -= 0
            is Sword -> plagueSize -= 1
        }
        if (plagueSize < 0) {
            plagueSize = 0
        }
    }

    override fun needsToExpand(): Boolean {
        if(plagueSize >= 3){
            return true
        }
        return false
    }

    override fun colonizedBy(plague: Colony): Colony {
        when(plague) {
            is Ant -> return this
            is Dragon -> {this.plagueSize = plagueSize+plague.plagueSize}
        }
        return this
    }

    override fun expand(position: Position, maxPosition: Position): List<Colonization>{

        val newPos = Position(((0..(maxPosition.x)).random()), ((0..(maxPosition.y)).random()))
        return listOf(Colonization(this, newPos))
    }
}