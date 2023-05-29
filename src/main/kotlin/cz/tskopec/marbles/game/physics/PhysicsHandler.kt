package cz.tskopec.marbles.game.physics

import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.map.objects.Marble
import cz.tskopec.marbles.game.map.objects.Collideable
import org.locationtech.jts.index.quadtree.Quadtree


class PhysicsHandler(obstacleTree: Quadtree) {

	private val obstacles = obstacleTree.queryAll().filterIsInstance<Collideable>()
	private val ballsInGame get() = GameController.players.flatMap { it.marbles }


	fun update(elapsedSeconds: Double) {

		moveBalls(elapsedSeconds)
		allCollisions().forEach { it.resolve() }
	}

	private fun moveBalls(elapsedSeconds: Double) {

		ballsInGame.forEach {
			with(it) {
				centerX += direction.x * speed * elapsedSeconds
				centerY += direction.y * speed * elapsedSeconds
				speed = (speed - Settings.frictionProperty.get()).coerceAtLeast(0.0)
			}
		}
	}

	private fun allCollisions(): Collection<Collision> {

		val balls = ballsInGame
		return balls.flatMapIndexed { i, ball ->
			val otherBallCollisions = ball.findCollisions(balls.subList(i + 1, balls.size))
			val obstacleCollisions = ball.findCollisions(obstacles)
			otherBallCollisions + obstacleCollisions
		}
	}

	private fun Marble.findCollisions(otherObjects: List<Collideable>): Collection<Collision>
		= otherObjects.filter{ this.overlaps(it) }.mapNotNull { it.findCollisionWith(this) }


}