package cz.tskopec.marbles.util

import cz.tskopec.marbles.game.map.objects.Cell
import cz.tskopec.marbles.game.map.objects.RoundObject
import org.locationtech.jts.geom.Geometry
import kotlin.math.sqrt


inline fun <reified T> Geometry.getPartsOfType() = List(numGeometries) { getGeometryN(it) }.filterIsInstance<T>()

fun cellUnion(cells: Set<Cell>): Geometry
    = cells
        .map { it.jtsPolygon }
        .reduce { p: Geometry, q: Geometry -> p.union(q) }


fun centersDistance(a: RoundObject, b: RoundObject): Double {
    val dX = a.centerX - b.centerX
    val dY = a.centerY - b.centerY
    return sqrt(dX * dX + dY * dY)
}

