package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.map.GameMap

object MapFactory {

	fun create(): GameMap {

		val mesh = MeshBuilder.build()
		val scheme = SchemeBuilder.build(mesh)
		return GameMap(scheme).apply{
			insertObstacles(screenEdges())
			insertObstacles(findWallEdges(scheme))
			insertObstacles(spawnHoles(this))
		}
	}
}