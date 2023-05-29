package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.map.objects.Cell
import cz.tskopec.marbles.util.cellUnion

data class Scheme(
    var wallCells: Set<Cell>,
    var emptyCells: Set<Cell>
) {

    val emptySpace = cellUnion(emptyCells)

}