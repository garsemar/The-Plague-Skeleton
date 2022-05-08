package theplague.logic.enemies

import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import theplague.logic.item.weapon.Weapon

sealed class Colony() : Iconizable {
    var plagueSize: Int = 1;
    var maxPlagueSize: Int = 3;
    open fun willReproduce(): Boolean{
        return false
    }

    fun reproduce(){
        if(plagueSize >= maxPlagueSize)
            needsToExpand()
        else
            plagueSize++;

    }
    open fun needsToExpand() : Boolean{
        return true
    }
    open fun attacked(weapon: Weapon) {}
    fun colonizedBy(plague: Colony): Colony {
        return plague
    }
    open fun expand(position: Position, maxPosition: Position): List<Colonization>{
        return listOf()
    }
}

