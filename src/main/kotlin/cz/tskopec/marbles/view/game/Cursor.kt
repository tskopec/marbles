package cz.tskopec.marbles.view.game

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.control.PlayerController
import cz.tskopec.marbles.game.map.objects.DEFAULT_MARBLE_RADIUS
import cz.tskopec.marbles.view.game.Cursor.ShapeBuilder.cursorCue
import cz.tskopec.marbles.view.game.Cursor.ShapeBuilder.cursorRing
import javafx.beans.binding.Bindings
import javafx.scene.Group
import javafx.scene.effect.BoxBlur
import javafx.scene.paint.Color
import javafx.scene.shape.Circle
import javafx.scene.shape.Line
import javafx.scene.shape.Shape
import javafx.scene.transform.Rotate

// Visual element around the currently selected marble indicating aim direction and strike strength
class Cursor(private val controller: PlayerController) : Group() {

    private val ring = cursorRing()
    private val cue = cursorCue()

    init {
        children.addAll(ring, cue)
        bindCursor()
        controller.updateSelection()
    }

    private fun bindCursor() {

        controller.selectedMarble.addListener { _, _, newVal ->
            if (newVal != null) {
                ring.centerXProperty().bind(newVal.centerXProp)
                ring.centerYProperty().bind(newVal.centerYProp)
            }
        }
        visibleProperty().bind(Bindings.isNotNull(controller.selectedMarble))

        val cueLength = Bindings.createDoubleBinding(
            { DEFAULT_MARBLE_RADIUS * 2 + controller.strikeStrength.get() / 2.0 },
            controller.strikeStrength
        )

        cue.startXProperty().bind(ring.centerXProperty())
        cue.startYProperty().bind(ring.centerYProperty())
        cue.endXProperty().bind(cue.startXProperty())
        cue.endYProperty().bind(cue.startYProperty().add(cueLength.multiply(-1)))

        cue.transforms += Rotate().apply {
            pivotXProperty().bind(cue.startXProperty())
            pivotYProperty().bind(cue.startYProperty())
            angleProperty().bind(controller.cueAngle.multiply(-1))
        }
    }

    private object ShapeBuilder {

        private val blurEffect = BoxBlur(2.0, 2.0, 1)

        fun cursorRing() = Circle(DEFAULT_MARBLE_RADIUS * 1.5, Color.TRANSPARENT).cursorStyle()

        fun cursorCue() = Line().cursorStyle()

        private fun <T : Shape> T.cursorStyle() = apply {
            strokeProperty().bind(Settings.cursorColorProperty)
            strokeWidth = 3.3
            effect = blurEffect
        }
    }
}







