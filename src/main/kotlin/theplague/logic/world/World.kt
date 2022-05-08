package theplague.logic.world

import theplague.interfaces.*
import theplague.logic.enemies.Ant
import theplague.logic.Player
import theplague.logic.Territory
import theplague.logic.enemies.Colonization
import theplague.logic.enemies.Colony
import theplague.logic.item.Item
import theplague.logic.item.vehicle.Vehicle
import theplague.logic.item.vehicle.vehicles.OnFoot
import theplague.logic.item.weapon.Weapon
import kotlin.math.*

class World(
    override val width: Int,
    override val height: Int,
    override val player: Player = Player(Position(width / 2, height / 2)),
    override val territories: List<List<Territory>> = List(height) { x ->
        List(width) { y ->
            Territory(
                Position(x, y)
            )
        }
    },
): IWorld {

    var takeableItem : Item? = null;
    var colonization : Colonization? = null;
    var actualPlayerTerritory = territories[width/2][height/2];

    init {
        territories[width/2][height/2].setPlayer(player);
        // Spawn new colony
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].summonPlague(Ant())
        this.player.turns++;
    }

    private fun getRandomWorldPosition() : Position {
        return Position((0 until width).random(), (0 until height).random())
    }

    override fun nextTurn() {

        if(player.currentVehicle.timesLeft == 0)
            player.currentVehicle = OnFoot(-1);
        else
            player.currentVehicle.timesLeft--;

        generateNewColonies()
        generateNewItems()
        reproducePlague()
        expandPlague()
    }

    private fun generateNewColonies() {
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].summonPlague()
    }

    private fun reproducePlague() {
        territories.flatten().forEach { if(it.position.x != player.position.x && it.position.x != player.position.y) it.reproducePlague(Position(width, height)) }
    }

    private fun expandPlague() {

        territories.flatten().forEach {
            val colony = it.plague;
            colony?.expand(it.position, 1)
        }

        territories.flatten().forEach {
            it.plague?.let { it1 -> Colonization(it1, it.position) }
        }
    }

    private fun generateNewItems() {
        val itemPos = getRandomWorldPosition();
        territories[itemPos.x][itemPos.y].spawnItem()
    }

    override fun gameFinished(): Boolean {
        return false
    }

    override fun canMoveTo(targetPos: Position) : Boolean{
        return player.currentVehicle.canMove(player.position, targetPos)
    }

    override fun moveTo(nextPosition: Position) {
        // Remove player from territory
        actualPlayerTerritory.removePlayer()

        // Move player
        player.position = nextPosition;
        actualPlayerTerritory = territories[nextPosition.y][nextPosition.x]

        actualPlayerTerritory.setPlayer(player)
        takeableItem = actualPlayerTerritory.item
        actualPlayerTerritory.removeItem()

        exterminate();
        this.player.turns++;
    }
    override fun exterminate() {
        actualPlayerTerritory.attackPlague()
    }

    override fun takeableItem(): Iconizable? {

        return takeableItem
    }

    override fun takeItem() {
        if(takeableItem != null) {
            when(takeableItem) {
                is Vehicle -> player.currentVehicle = takeableItem as Vehicle
                is Weapon -> player.currentWeapon = takeableItem as Weapon
            }
        }
    }
}