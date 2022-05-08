package theplague.logic.enemies

import theplague.interfaces.Position
import theplague.logic.item.weapon.Weapon
import theplague.logic.item.weapon.weapons.Broom
import theplague.logic.item.weapon.weapons.Hand
import theplague.logic.item.weapon.weapons.Sword

class Ant() : Colony() {
    override val icon : String = "\uD83D\uDC1C"

    var reproductionTax: Int = 30;

    override fun willReproduce() : Boolean {
        if((0 until 100).random() in 0 until reproductionTax) {
            return true
        }
        return false;
    }

    override fun attacked(weapon: Weapon){
        when(weapon){
            is Hand -> plagueSize -= 2
            is Broom -> plagueSize -= 3
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
    fun expand(position: Position, maxPosition: Position): List<Colonization>{

        val posList = mutableListOf<Position>()
        while (posList.size != 4){
            var num = Position((0..2).random(), (0..2).random())
            num = Position((position.x - 1) + num.x, (position.y - 1) + num.y)
            if(num !in posList){
                posList.add(num)
            }
        }
        return listOf(Colonization(this, posList[0]))
    }


}

