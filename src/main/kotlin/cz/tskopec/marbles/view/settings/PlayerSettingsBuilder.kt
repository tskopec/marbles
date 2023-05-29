package cz.tskopec.marbles.view.settings

import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.input.Action
import cz.tskopec.marbles.game.input.KeyHandler
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color

// pane containing components for editing the settings of a single player
fun buildPlayerSettingsView(player: Player): Region = GridPane().apply {

    hgap = 10.0
    vgap = 10.0
    padding = Insets(10.0)

    addRow(0, *textInput("Name: ", player.nameProperty))
    addRow(1, *colorInput("Color: ", player.colorProperty))
    addRow(2, *sliderInput("Aim sensitivity: ", player.controller.aimSensitivity))
    addRow(3, *sliderInput("Shoot sensitivity: ", player.controller.strikeSensitivity))
    KeyHandler.controls.filter { it.value.owner == player }.forEach { (code, action) ->
       addRow(this.rowCount, setKeyButton(action, code, this))
    }


    backgroundProperty().bind(Bindings.createObjectBinding({
        Background(BackgroundFill(player.colorProperty.get().interpolate(Color.WHITE, 0.66), null, null))
    }, player.colorProperty))

}

// button for binding a key code to the specified action
private fun setKeyButton(action: Action, oldCode: KeyCode, view: Region): Node {

    var currentCode = oldCode

    val button = Button("${action.description}   -   [${currentCode.name}]").apply{
        maxWidth = Double.MAX_VALUE
        GridPane.setHgrow(this, Priority.ALWAYS)
    }

    val handler = object : EventHandler<KeyEvent> {

        override fun handle(event: KeyEvent?) {
            val newCode = event?.code ?: return
            if (!KeyHandler.controls.containsKey(newCode)) {
                KeyHandler.controls.remove(currentCode)
                KeyHandler.controls[newCode] = action
                currentCode = newCode
                button.text = "${action.description}  [${currentCode.name}]"
            }
            else
                Alert(Alert.AlertType.ERROR, "Key [${newCode.name}] is already used", ButtonType.OK).show()
            view.removeEventHandler(KeyEvent.KEY_RELEASED, this)
        }
    }

    button.apply {
        setOnAction {
            view.addEventHandler(KeyEvent.KEY_RELEASED, handler)
            view.requestFocus()
        }
    }
    GridPane.setColumnSpan(button, 2)
    return button
}




