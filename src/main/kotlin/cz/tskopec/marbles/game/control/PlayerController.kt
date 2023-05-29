package cz.tskopec.marbles.game.control

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.map.objects.Marble
import javafx.beans.binding.Bindings
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.value.ObservableObjectValue
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import org.locationtech.jts.math.Vector2D
import kotlin.math.cos
import kotlin.math.sign
import kotlin.math.sin

class PlayerController {

    val cueAngle = SimpleDoubleProperty(0.0)
    val strikeStrength = SimpleDoubleProperty(0.0)
    val aimSensitivity = SimpleDoubleProperty(0.5)
    val strikeSensitivity = SimpleDoubleProperty(0.5)


    val marbles: ObservableList<Marble> = FXCollections.observableArrayList()
    private val selectedMarbleIndex = SimpleIntegerProperty(-1)
    val selectedMarble: ObservableObjectValue<Marble> = Bindings.valueAt(marbles, selectedMarbleIndex)
    

    init {
        marbles.addListener(ListChangeListener { updateSelection() })
    }
    

    fun updateSelection() {

        if(marbles.isEmpty())
            selectedMarbleIndex.set(-1)
        else
            selectedMarbleIndex.set(selectedMarbleIndex.get().coerceIn(0 until marbles.size))
    }


    fun turnCue(direction: Int, elapsedSeconds: Double) {

        cueAngle.set((cueAngle.get() + aimSensitivity.value * direction.sign * elapsedSeconds * 360.0) % 360.0)
    }

    fun incrementStrength(elapsedSeconds: Double) {

        strikeStrength.set(
            (strikeStrength.get() + strikeSensitivity.value * elapsedSeconds * Settings.maxStrengthProperty.value)
                .coerceIn(0.0, Settings.maxStrengthProperty.value)
        )
    }

    fun strikeMarble() {

        selectedMarble.get()
            ?.takeUnless { it.isMoving() }
            ?.apply {
                direction = Vector2D(sin(Math.toRadians(cueAngle.get())), cos(Math.toRadians(cueAngle.get())))
                speed = strikeStrength.get()
            }
        strikeStrength.set(0.0)

    }

    fun changeSelectedMarble(direction: Int) {

        val nMarbles = marbles.size
        selectedMarbleIndex.set((selectedMarbleIndex.get() + nMarbles + direction.sign) % nMarbles)
    }
}