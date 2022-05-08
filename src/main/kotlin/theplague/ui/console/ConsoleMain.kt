package theplague.ui.console

import java.util.*
import theplague.logic.world.World

fun main() {
    val world = World(10,10)
    val scanner = Scanner(System.`in`)
    val worldUi = WorldUi(scanner, world)
    worldUi.play()
}