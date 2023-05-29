package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.map.objects.Cell
import cz.tskopec.marbles.game.map.objects.WallEdge
import org.locationtech.jts.geom.Coordinate


fun screenEdges() = listOf(
	WallEdge(Coordinate(Settings.mapWidth, 0.0), Coordinate(0.0, 0.0)),
	WallEdge(Coordinate(0.0, 0.0), Coordinate(0.0, Settings.mapHeight)),
	WallEdge(Coordinate(0.0, Settings.mapHeight), Coordinate(Settings.mapWidth, Settings.mapHeight)),
	WallEdge(Coordinate(Settings.mapWidth, Settings.mapHeight), Coordinate(Settings.mapWidth, 0.0))
)

fun findWallEdges(scheme: Scheme): List<WallEdge> {

	fun Collection<Cell>.allEdges() = flatMap { it.edges }.toSet()

	fun outerEdges(cells: Set<Cell>): List<WallEdge> {

		return cells.allEdges()
			.groupBy { it.segment }
			.values
			.filter { it.size == 1 }
			.flatten()
	}

	return outerEdges(scheme.wallCells)
}