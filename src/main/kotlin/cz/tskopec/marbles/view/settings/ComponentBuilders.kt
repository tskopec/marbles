package cz.tskopec.marbles.view.settings

import javafx.beans.property.DoubleProperty
import javafx.beans.property.IntegerProperty
import javafx.beans.property.ObjectProperty
import javafx.beans.property.StringProperty
import javafx.scene.control.ColorPicker
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.paint.Color


fun textInput(description: String, boundProperty: StringProperty) = arrayOf(
    Label(description),
    TextField().apply {
        textProperty().set(boundProperty.get())
        textProperty().bindBidirectional(boundProperty)
    }
)

fun sliderInput(description: String, boundProperty: DoubleProperty, min: Double = 0.0, max: Double = 1.0) = arrayOf(
    Label(description),
    Slider(min, max, min).apply {
        valueProperty().set(boundProperty.get())
        valueProperty().bindBidirectional(boundProperty)
    }
)

fun countInput(description: String, boundProperty: IntegerProperty, min: Int = 0) = arrayOf(
    Label(description),
    TextField().apply {
        textProperty().set(boundProperty.get().toString())
        textProperty().addListener { _, _, new ->
            val count = new.toIntOrNull() ?: return@addListener
            boundProperty.set(count.coerceAtLeast(min))
        }
    }
)

fun doubleInput(description: String, boundProperty: DoubleProperty, min: Double = 0.0) = arrayOf(
    Label(description),
    TextField().apply {
        textProperty().set(boundProperty.get().toString())
        textProperty().addListener { _, _, newVal ->
            val num = newVal.toDoubleOrNull() ?: return@addListener
            boundProperty.set(num.coerceAtLeast(min))
        }
    }
)

fun colorInput(description: String, boundProperty: ObjectProperty<Color>) = arrayOf(
    Label(description),
    ColorPicker().apply { valueProperty().bindBidirectional(boundProperty) }
)
