package cz.tskopec.marbles.game

import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import cz.tskopec.marbles.util.randomColor
import javafx.beans.binding.Bindings
import javafx.scene.paint.Color
import kotlin.math.roundToInt

object Settings {

    const val nPlayers = 2
    val ballsPerPlayerProperty = SimpleIntegerProperty(3)
    val nHolesProperty = SimpleIntegerProperty(3)

    val widthProperty = SimpleDoubleProperty(1000.0)
    val heightProperty = SimpleDoubleProperty(600.0)
    val mapWidth: Double get() = widthProperty.get()
    val mapHeight: Double get() = heightProperty.get()

    val smoothnessProperty = SimpleDoubleProperty(0.5)
    val densityProperty = SimpleDoubleProperty(0.5)
    val frictionProperty = SimpleDoubleProperty(0.2)
    val maxStrengthProperty = SimpleDoubleProperty(200.0)

    val backgroundColorProperty = SimpleObjectProperty(randomColor())
    val obstacleColorProperty = SimpleObjectProperty(randomColor())
    val cursorColorProperty = SimpleObjectProperty(Color.WHITE)

    fun randomizeMapColors(){
        Settings.backgroundColorProperty.set(randomColor())
        Settings.obstacleColorProperty.set(randomColor())
    }
}