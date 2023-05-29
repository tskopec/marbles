package cz.tskopec.marbles.view.game

import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.Settings.obstacleColorProperty
import javafx.beans.binding.Bindings
import javafx.beans.property.DoubleProperty
import javafx.beans.value.ObservableObjectValue
import javafx.scene.effect.Lighting
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Polygon
import javafx.scene.shape.Shape
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Polygon as JTSPolygon


fun circleShape(
	center: Coordinate,
	radius: Double
) = Circle().also {
        it.centerX = center.x
        it.centerY = center.y
        it.radius = radius
    }

fun Circle.toMarble(xProp: DoubleProperty, yProp: DoubleProperty, owner: Player) = also {
	it.centerXProperty().bind(xProp)
	it.centerYProperty().bind(yProp)
	it.fillProperty().bind(owner.colorProperty)
	it.stroke = Color.BLACK
	it.strokeWidth = 1.0
	it.effect = Lighting()
}

fun Circle.toHole() = also { it.fill = Color.BLACK }


fun cellShape(jtsPoly: JTSPolygon): Shape =
	Polygon(*jtsPoly.coordinates.flatMap { listOf(it.x, it.y) }.toDoubleArray()).apply {
		fillProperty().bind(obstacleColorProperty)
		strokeProperty().bind(edgeColor)
	}


fun wallShape(p1: Coordinate, p2: Coordinate): Shape = Line(p1.x, p1.y, p2.x, p2.y).apply {
	this.strokeWidth = 2.3
	strokeProperty().bind(edgeColor)
}


val edgeColor: ObservableObjectValue<Color>
	= Bindings.createObjectBinding({
		val baseColor = obstacleColorProperty.get()
		val endColor = if(baseColor.brightness < 0.5) Color.WHITE else Color.BLACK
		baseColor.interpolate(endColor, 0.8)
	}, obstacleColorProperty)

