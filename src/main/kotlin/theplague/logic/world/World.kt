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

    /**
     * Function that returns a random position on the map.
     */
    private fun getRandomWorldPosition() : Position {
        return Position((0 until width).random(), (0 until height).random())
    }

    /**
     * This function is called at the end of each turn.
     */
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

    /**
     * This function calls another function from the position of the list you pass it.
     */
    private fun generateNewColonies() {
        val colonyPos = getRandomWorldPosition();
        territories[colonyPos.x][colonyPos.y].summonPlague()
    }

    /**
     * This function checks that the player is not in the territory and calls the reproducePlague function.
     */
    private fun reproducePlague() {
        territories.flatten().forEach { if(it.position.x != player.position.x && it.position.x != player.position.y) it.reproducePlague(Position(width, height)) }
    }

    /**
     * This function calls the expand function passing as parameter the class Position and the maxPosition.
     */
    private fun expandPlague() {
        territories.flatten().forEach {
            val colony = it.plague;
            colony?.expand(it.position, 1)
        }
    }

    /**
     * This function calls for each territory the function spawnItem.
     */
    private fun generateNewItems() {
        val itemPos = getRandomWorldPosition();
        territories[itemPos.x][itemPos.y].spawnItem()
    }

    /**
     * This function returns true if the game ends.
     */
    override fun gameFinished(): Boolean {
        return false
    }

    /**
     * This function checks if the position the player wants to move to is possible.
     */
    override fun canMoveTo(targetPos: Position) : Boolean{
        return player.currentVehicle.canMove(player.position, targetPos)
    }

    /**
     * This function moves the player to the position that is passed as a parameter.
     */
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
        player.turns++;
    }

    /**
     * This function calls the function attackPlague.
     */
    override fun exterminate() {
        actualPlayerTerritory.attackPlague()
    }

    /**
     * This function calls the function that is in charge of printing the item that the user is going to take.
     */
    override fun takeableItem(): Iconizable? {
        return takeableItem
    }

    /**
     * This function checks if the item is a weapon or a vehicle and assigns the one taken by the user.
     */
    override fun takeItem() {
        if(takeableItem != null) {
            when(takeableItem) {
                is Vehicle -> player.currentVehicle = takeableItem as Vehicle
                is Weapon -> player.currentWeapon = takeableItem as Weapon
            }
        }
    }
}