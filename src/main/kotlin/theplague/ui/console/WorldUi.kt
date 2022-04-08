package theplague.ui.console

import theplague.interfaces.ITerritory
import theplague.interfaces.IWorld
import theplague.interfaces.Iconizable
import theplague.interfaces.Position
import java.util.*

class WorldUi(val scanner: Scanner = Scanner(System.`in`), val world: IWorld){
    private val currentPlayerWeaponIcon get() = world.player.currentWeapon.icon
    private val currentPlayerVehicleIcon get() = world.player.currentVehicle.icon
    val empty = "  "

    fun validPosition(position: Position) = position.x in 0.. world.width-1 && position.y in 0..world.height-1

    fun queryPosition() : Position {
        var position : Position
        do{
            println("Where do you want to go? ${currentPlayerWeaponIcon} ${currentPlayerVehicleIcon}")
            val x = scanner.nextInt()-1
            val y = scanner.nextInt()-1
            position = Position(x, y)
        } while(! validPosition(position))
        return position
    }

    private fun queryTakeItem(item: Iconizable) :Boolean {
        var response : String
        do{
            println("Do you want to pick the ${item.icon} (y/n)?")
            response = scanner.next()
        } while(response!="y" && response!="n")
        return response=="y"
    }

    fun nextTurn(){
        draw()
        var position = queryPosition()
        while(!world.canMoveTo(position)){
            println("You can't go to this position with the current vehicle")
            position = queryPosition()
        }
        world.moveTo(position)
        val item = world.takeableItem()
        if(item!=null){
            if(queryTakeItem(item))
                world.takeItem()
        }
        world.exterminate()
        world.nextTurn()
    }



    fun drawFirtLine(territory: ITerritory) {
        print(territory.iconList().getOrNull(0)?.icon ?: empty)
        print(territory.iconList().getOrNull(1)?.icon ?: empty)
    }

    fun drawSecondLine(territory: ITerritory) {
        print(territory.iconList().getOrNull(2)?.icon ?: empty)
        print(territory.iconList().getOrNull(3)?.icon ?: empty)
    }

    fun draw(){
        drawHorizontalLine()
        world.territories.forEach{ row->
            row.forEach{  cell ->
                print("|")
                drawFirtLine(cell)
            }
            println("|")
            row.forEach{  cell ->
                print("|")
                drawSecondLine(cell)
            }
            println("|")
            drawHorizontalLine()
        }
    }
    private fun drawHorizontalLine() {
        repeat(world.width*5){print("-")}
        println()
    }

    fun play() {
        while(!world.gameFinished()) {
            nextTurn()
        }
        println("You have lived ${world.player.turns} turns")
    }
}