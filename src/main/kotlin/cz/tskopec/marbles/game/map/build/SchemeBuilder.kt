package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.map.objects.Cell
import cz.tskopec.marbles.util.allPairs
import cz.tskopec.marbles.util.bfSearch
import cz.tskopec.marbles.util.getPartsOfType
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.Polygon

// Converts the mesh of triangles into map cells, and divides these into wall cells and empty cells
object SchemeBuilder {

    fun build(mesh: Geometry): Scheme {

        val cells = mesh.convertToCells()
        val graph = buildCellGraph(cells)
        val emptyCells = tunnel(graph)
        val wallCells = cells.minus(emptyCells)
        return Scheme(wallCells, emptyCells)
    }

    private fun Geometry.convertToCells() = getPartsOfType<Polygon>().map(Cell.Factory::fromTriangle).toSet()


    // creates a graph where each cell is linked to all its neighbours
    private fun buildCellGraph(cells: Collection<Cell>): Map<Cell, List<Cell>> {

        return cells.flatMap { it.edgeToCellPairs }
            .groupBy({ (edge, _) -> edge.segment }, { (_, cell) -> cell})
            .values
            .filter { it.size == 2 }
            .flatMap { allPairs(it) }
            .groupBy({ it.first }, { it.second })
    }

    // selects some subset of connected cells from the graph by repeatedly selecting two random cells and all the cells constituting
    // the shortest path between them, until the selected set reaches size prescribed by the desired map density
    private fun tunnel(cellGraph: Map<Cell, List<Cell>>): Set<Cell> {

        val tunnelCells = mutableSetOf<Cell>()
        val allCells = cellGraph.keys.shuffled()
        val nCells = allCells.size
        if (nCells < 2)
            return tunnelCells
        val targetTunnelSize = nCells - nCells * Settings.densityProperty.get()

        val iterator = allCells.iterator()
        var start = iterator.next()
        var end = iterator.next()

        while (true) {
            val connectingPath = bfSearch(start, end, cellGraph)
            for (pathCell in connectingPath) {
                tunnelCells += pathCell
                if (tunnelCells.size >= targetTunnelSize)
                    return tunnelCells
            }
            start = end
            end = iterator.next()
        }
    }
}