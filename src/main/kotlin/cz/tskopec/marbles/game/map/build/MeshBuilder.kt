package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.Settings
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder
import kotlin.random.Random

object MeshBuilder : GeometryFactory() {

    private const val MIN_VERTEX_DISTANCE = 50.0
    private const val MAX_VERTICES = 1000
    private const val PADDING = 23.0
    private val geomFact = GeometryFactory()

    fun build(): Geometry {

        val nVertices = (MAX_VERTICES * Settings.smoothnessProperty.get()).toInt()
        val triBuilder = DelaunayTriangulationBuilder()
        triBuilder.setTolerance(MIN_VERTEX_DISTANCE)
        triBuilder.setSites(randomPoints(nVertices) + cornerPoints())
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

