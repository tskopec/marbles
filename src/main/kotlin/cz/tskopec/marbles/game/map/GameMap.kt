package cz.tskopec.marbles.game.map

import cz.tskopec.marbles.game.map.build.Scheme
import cz.tskopec.marbles.game.map.objects.Collideable
import cz.tskopec.marbles.game.map.objects.Renderable
import org.locationtech.jts.index.quadtree.Quadtree

class GameMap(
	val scheme: Scheme, // contains the cells
	val obstacles: Quadtree = Quadtree() // contains non-moving collideable objects: wall edges and holes
) {

	fun insertObstacles(objects: List<Collideable>) = objects.forEach { obstacles.insert(it.envelope, it) }

	fun allTerrain(): Set<Renderable>
		= scheme.wallCells.plus(obstacles.queryAll().filterIsInstance<Renderable>())

}