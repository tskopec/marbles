package cz.tskopec.marbles.game.map.objects

import cz.tskopec.marbles.game.physics.Collision
import javafx.scene.shape.Shape
import org.locationtech.jts.geom.Envelope
import org.locationtech.jts.index.quadtree.Quadtree
import org.locationtech.jts.math.Vector2D


interface Renderable {

	val shape: Shape
}

interface Collideable {

	val envelope: Envelope

	fun findCollisionWith(marble: Marble): Collision?

	fun overlaps(other: Collideable): Boolean
		= this.envelope.intersects(other.envelope)

	fun notOverlappingAnyOf(obstacles: Iterable<Collideable>): Boolean
			= obstacles.none(::overlaps)

	fun notOverlappingAnyOf(obstacles: Quadtree): Boolean {
		val obstacleList = obstacles.query(this.envelope).filterIsInstance<Collideable>()
		return notOverlappingAnyOf(obstacleList)
	}
}

interface Movable {

	var direction: Vector2D
	var speed: Double

	fun isMoving(): Boolean { return speed != 0.0 }

	fun scaledMoveVector(): Vector2D = direction.multiply(speed)

	fun setSpeedAndDirection(scaled: Vector2D){
		speed = scaled.length()
		direction = scaled.normalize()
	}
}










