package cz.tskopec.marbles.game.map.objects

import cz.tskopec.marbles.view.game.cellShape
import org.locationtech.jts.geom.Polygon

// triangular cell
class Cell private constructor(val jtsPolygon: Polygon): Renderable {

    override val shape = cellShape(jtsPolygon)

    val edges = listOf(
        WallEdge(jtsPolygon.coordinates[0], jtsPolygon.coordinates[1]),
        WallEdge(jtsPolygon.coordinates[1], jtsPolygon.coordinates[2]),
        WallEdge(jtsPolygon.coordinates[2], jtsPolygon.coordinates[0])
    )
    val edgeToCellPairs = edges.map { it to this }


    companion object Factory {

        fun fromTriangle(triangle: Polygon): Cell = with(triangle) {
            if (numPoints == 4 && coordinates[0].equals2D(coordinates[3]))
                Cell(this)
            else throw NotATriangleException(triangle)
        }
    }

    class NotATriangleException(polygon: Polygon) : Exception("Polygon <$polygon> is not a triangle")
}



