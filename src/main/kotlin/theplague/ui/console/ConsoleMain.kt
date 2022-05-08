package theplague.ui.console

import theplague.logic.world.World
import java.util.*

fun main() {
    val world = World(10,10)
    val scanner = Scanner(System.`in`)
    val worldUi = WorldUi(scanner, world)
    worldUi.play()
}