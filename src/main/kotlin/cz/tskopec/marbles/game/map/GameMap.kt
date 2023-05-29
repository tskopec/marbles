package cz.tskopec.marbles.game.map

import cz.tskopec.marbles.game.map.build.Scheme
import cz.tskopec.marbles.game.map.objects.Collideable
import cz.tskopec.marbles.game.map.objects.Renderable
import org.locationtech.jts.index.quadtree.Quadtree

class GameMap(
	val scheme: Scheme,
	val obstacles: Quadtree = Quadtree()
) {

	fun insertObstacles(objects: List<Collideable>) = objects.forEach { obstacles.insert(it.envelope, it) }

	fun allTerrain(): Set<Renderable>
		= scheme.wallCells.plus(obstacles.queryAll().filterIsInstance<Renderable>())

}