package theplague.ui.console

import theplague.logic.World
import java.util.*

fun main() {
    val world = World()
    val scanner = Scanner(System.`in`)
    val worldUi = WorldUi(scanner, world)
    worldUi.play()



}