package cz.tskopec.marbles.game.map.objects

import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.physics.Collision
import cz.tskopec.marbles.game.physics.HoleCollision
import cz.tskopec.marbles.game.physics.MarbleCollision
import cz.tskopec.marbles.util.centersDistance
import cz.tskopec.marbles.view.game.circleShape
import cz.tskopec.marbles.view.game.toBall
import cz.tskopec.marbles.view.game.toHole
import javafx.beans.property.SimpleDoubleProperty
import javafx.scene.shape.Circle
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Envelope
import org.locationtech.jts.math.Vector2D

const val DEFAULT_BALL_RADIUS = 8.0

open class RoundObject(center: Coordinate) : Renderable, Collideable {

    val radius: Double = DEFAULT_BALL_RADIUS
    val centerXProp = SimpleDoubleProperty(center.x)
    val centerYProp = SimpleDoubleProperty(center.y)

    var centerX
        get() = centerXProp.get()
        set(v) = centerXProp.set(v)
    var centerY
        get() = centerYProp.get()
        set(v) = centerYProp.set(v)

    override val shape: Circle = circleShape(center, radius)

    override val envelope = currentEnvelope()

    override fun findCollisionWith(marble: Marble): Collision? = null

    protected fun currentEnvelope() = Envelope(
        Coordinate(centerX - radius, centerY - radius),
        Coordinate(centerX + radius, centerY + radius)
    )
}

class Marble(base: RoundObject, val owner: Player) : RoundObject(Coordinate(base.centerX, base.centerY)), Movable {

    override val shape = super.shape.toBall(centerXProp, centerYProp, owner)

    override var direction = Vector2D(0.0, 0.0)
    override var speed = 0.0

    override val envelope get() = currentEnvelope()

    override fun findCollisionWith(marble: Marble): Collision? =
        if (centersDistance(this, marble) <= this.radius + marble.radius)
            MarbleCollision(marble, this)
        else null
}


class Hole(base: RoundObject, private val tolerance: Double = 10.0) : RoundObject(Coordinate(base.centerX, base.centerY)) {

    override val shape: Circle = super.shape.toHole()

    override fun findCollisionWith(marble: Marble): Collision? =
        if (centersDistance(marble, this) < this.tolerance)
            HoleCollision(marble)
        else null
}