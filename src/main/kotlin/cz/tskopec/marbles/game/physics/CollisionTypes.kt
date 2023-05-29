package cz.tskopec.marbles.game.physics

import cz.tskopec.marbles.game.map.objects.*
import cz.tskopec.marbles.util.centersDistance
import org.locationtech.jts.math.Vector2D


sealed class Collision() {

    abstract fun resolve()
}


class MarbleCollision(private val thisMarble: Marble, private val otherMarble: Marble) : Collision() {


    override fun resolve() {

        //https://en.wikipedia.org/wiki/Elastic_collision#Two-dimensional_collision_with_two_moving_objects
        val thisMoveVector = thisMarble.scaledMoveVector()
        val otherMoveVector = otherMarble.scaledMoveVector()

        val centersDifference =
            Vector2D(thisMarble.centerX - otherMarble.centerX, thisMarble.centerY - otherMarble.centerY)
        val moveDifference = Vector2D(thisMoveVector.x - otherMoveVector.x, thisMoveVector.y - otherMoveVector.y)
        val delta =
            centersDifference.multiply(moveDifference.dot(centersDifference) / centersDifference.lengthSquared())

        thisMarble.setSpeedAndDirection(thisMoveVector.subtract(delta))
        otherMarble.setSpeedAndDirection(otherMoveVector.add(delta))
    }
}

class EdgeCollision(private val thisMarble: Marble, private val edge: WallEdge) : Collision() {

    override fun resolve() {

        if (thisMarble.direction.dot(edge.normal) < 0.0) {
            val doubleDotProduct = thisMarble.direction.dot(edge.normal) * 2
            val directionDelta = Vector2D(doubleDotProduct * edge.normal.x, doubleDotProduct * edge.normal.y)
            thisMarble.direction = thisMarble.direction.subtract(directionDelta)
        }
    }
}

class HoleCollision(private val thisMarble: Marble) : Collision() {

    override fun resolve() {

        thisMarble.owner.let { player ->
            player.scoreProperty.value++
            player.controller.marbles.remove(thisMarble)
        }

    }
}