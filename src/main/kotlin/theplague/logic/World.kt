package theplague.logic

import theplague.interfaces.*




class World(override val width: Int,
            override val height: Int,
            override val territories : List<List<ITerritory>> = List(width) { List<ITerritory>(height) {Territory()} } ): IWorld {
    override val player : IPlayer = Player()

    override fun nextTurn() {

    }

    override fun gameFinished(): Boolean {
        return false
    }

    override fun canMoveTo(position: Position) : Boolean{
        return false
    }

    override fun moveTo(position: Position) {

    }

    override fun exterminate() {

    }

    override fun takeableItem(): Iconizable? {
        return null
    }

    override fun takeItem() {

    }
}