package cz.tskopec.marbles.game.physics

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.map.objects.Collideable
import cz.tskopec.marbles.game.map.objects.Marble
import org.locationtech.jts.index.quadtree.Quadtree


class PhysicsHandler(obstacleTree: Quadtree) {

	// all static collideable objects (wall edges and holes)
	private val obstacles = obstacleTree.queryAll().filterIsInstance<Collideable>()

	private val allMarbles get() = GameController.players.flatMap { it.marbles }


	fun update(elapsedSeconds: Double) {

		moveMarbles(elapsedSeconds)
		allCollisions().forEach { it.resolve() }
	}

	private fun moveMarbles(elapsedSeconds: Double) {

		allMarbles.forEach {
			with(it) {
				centerX += direction.x * speed * elapsedSeconds
				centerY += direction.y * speed * elapsedSeconds
				speed = (speed - Settings.frictionProperty.get()).coerceAtLeast(0.0)
			}
		}
	}

	private fun allCollisions(): Collection<Collision> {

		return allMarbles.flatMapIndexed { i, marble ->
			val otherMarbleCollisions = marble.findCollisions(allMarbles.subList(i + 1, allMarbles.size))
			val obstacleCollisions = marble.findCollisions(obstacles)
			otherMarbleCollisions + obstacleCollisions
		}
	}

	private fun Marble.findCollisions(otherObjects: List<Collideable>): Collection<Collision>
		= otherObjects.filter{ this.overlaps(it) }.mapNotNull { it.findCollisionWith(this) }


}