package theplague.logic

import theplague.interfaces.*


class World(override val width: Int,
            override val height: Int,
            override val territories : List<List<ITerritory>> = List(height) { x -> List<ITerritory>(width) { y -> Territory(Position(x,y))} } ): IWorld {


    override val player : Player = Player(Position(width/2,height/2))



    override fun nextTurn() {

    }

    override fun gameFinished(): Boolean {
        return false
    }

    override fun canMoveTo(position: Position) : Boolean{
        return true
    }

    override fun moveTo(position: Position) {
        player.position = position;
    }

    override fun exterminate() {

    }

    override fun takeableItem(): Iconizable? {
        return null
    }

    override fun takeItem() {

    }
}