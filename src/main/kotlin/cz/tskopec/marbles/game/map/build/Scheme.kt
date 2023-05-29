package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.map.objects.Cell
import cz.tskopec.marbles.util.cellUnion

// schema of a game map, containing cells designated as walls and empty space between them
class Scheme(
    var wallCells: Set<Cell>,
    emptyCells: Set<Cell>
) {

    val emptySpace = cellUnion(emptyCells)
}