package cz.tskopec.marbles.game

import cz.tskopec.marbles.util.randomColor
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.paint.Color

object Settings {

    const val nPlayers = 2
    val marblesPerPlayerProperty = SimpleIntegerProperty(3)
    val nHolesProperty = SimpleIntegerProperty(3)

    val widthProperty = SimpleDoubleProperty(1000.0)
    val heightProperty = SimpleDoubleProperty(600.0)
    val mapWidth: Double get() = widthProperty.get()
    val mapHeight: Double get() = heightProperty.get()

    val smoothnessProperty = SimpleDoubleProperty(0.5) // controls the number of points used to generate map
    val densityProperty = SimpleDoubleProperty(0.5) // controls the number of wall cells
    val frictionProperty = SimpleDoubleProperty(0.2)
    val maxStrengthProperty = SimpleDoubleProperty(200.0) // maximum strength for striking the marble

    val backgroundColorProperty = SimpleObjectProperty(randomColor())
    val obstacleColorProperty = SimpleObjectProperty(randomColor())
    val cursorColorProperty = SimpleObjectProperty(Color.WHITE)

    fun randomizeMapColors(){
        backgroundColorProperty.set(randomColor())
        obstacleColorProperty.set(randomColor())
    }
}