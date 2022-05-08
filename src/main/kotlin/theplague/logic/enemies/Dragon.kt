package theplague.logic.enemies

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
}