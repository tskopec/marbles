package cz.tskopec.marbles.game.map.objects

import cz.tskopec.marbles.game.physics.Collision
import cz.tskopec.marbles.game.physics.WallCollision
import cz.tskopec.marbles.view.game.wallShape
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Envelope
import org.locationtech.jts.geom.LineSegment
import org.locationtech.jts.math.Vector2D


class WallEdge(p0: Coordinate, p1: Coordinate) : Renderable, Collideable {

    val segment = LineSegment(p0, p1).apply { normalize() }
    val normal: Vector2D = Vector2D(p0, p1).rotateByQuarterCircle(3).normalize()

    override val shape = wallShape(p0, p1)

    override val envelope = Envelope(segment.p0, segment.p1)

    override fun findCollisionWith(marble: Marble): Collision? =
        if (segment.distance(Coordinate(marble.centerX, marble.centerY)) <= marble.radius)
            WallCollision(marble, this)
        else null

}