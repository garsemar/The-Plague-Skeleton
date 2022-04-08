package theplague.interfaces

interface ITerritory {
    /**
     * List of icons of the current territory (4 max)
     */
    fun iconList() : List<Iconizable>
}