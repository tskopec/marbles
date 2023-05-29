package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.Settings
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder
import kotlin.random.Random

// Creates Delaunay triangulation of a set of random points and four points in the corners of the window
object MeshBuilder : GeometryFactory() {

    private const val MIN_POINT_DISTANCE = 50.0
    private const val MAX_POINTS = 1000
    private const val PADDING = 23.0 // min distance of the random points from the window edges
    private val geomFact = GeometryFactory()

    fun build(): Geometry {

        val nPoints = (MAX_POINTS * Settings.smoothnessProperty.get()).toInt()
        val triBuilder = DelaunayTriangulationBuilder()
        triBuilder.setTolerance(MIN_POINT_DISTANCE)
        triBuilder.setSites(randomPoints(nPoints) + cornerPoints())
        return triBuilder.getTriangles(geomFact)
    }


    private fun randomPoints(n: Int): Collection<Coordinate> {

        return generateSequence {
            Coordinate(
                PADDING + Random.nextDouble() * (Settings.mapWidth - 2 * PADDING),
                PADDING + Random.nextDouble() * (Settings.mapHeight - 2 * PADDING)
            )
        }.distinct()
            .take(n)
            .toList()

    }

    private fun cornerPoints() = listOf(
        Coordinate(0.0, 0.0), Coordinate(Settings.mapWidth, 0.0),
        Coordinate(Settings.mapWidth, Settings.mapHeight), Coordinate(0.0, Settings.mapHeight)
    )
}

