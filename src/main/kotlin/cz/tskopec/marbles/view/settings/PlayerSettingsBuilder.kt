package cz.tskopec.marbles.view.settings

import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.input.Action
import cz.tskopec.marbles.game.input.KeyHandler
import javafx.beans.binding.Bindings
import javafx.event.EventHandler
import javafx.geometry.Insets
import javafx.scene.Node
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.layout.*
import javafx.scene.paint.Color


fun buildPlayerSettingsView(player: Player): Region = GridPane().apply {

    hgap = 10.0
    vgap = 10.0
    padding = Insets(10.0)

    addRow(0, *textInput("Name: ", player.nameProperty))
    addRow(1, *colorInput("Color: ", player.colorProperty))
    addRow(2, *sliderInput("Aim sensitivity: ", player.controller.aimSensitivity))
    addRow(3, *sliderInput("Shoot sensitivity: ", player.controller.strikeSensitivity))
    KeyHandler.controls.filter { it.value.owner == player }.forEach {
       addRow(this.rowCount, setKeyButton(it, this))
    }


    backgroundProperty().bind(Bindings.createObjectBinding({
        Background(BackgroundFill(player.colorProperty.get().interpolate(Color.WHITE, 0.66), null, null))
    }, player.colorProperty))

}

private fun setKeyButton(controlEntry: Map.Entry<KeyCode, Action>, view: Region): Node {

    var currentCode = controlEntry.key
    val action = controlEntry.value

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




