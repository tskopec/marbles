package cz.tskopec.marbles.view.game

import cz.tskopec.marbles.game.control.GameController
import javafx.beans.binding.Bindings
import javafx.beans.binding.StringBinding
import javafx.scene.control.Label
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Text


object UIDisplay {

    private val scoreTextProperty: StringBinding = Bindings.createStringBinding(
        {
            GameController.players.joinToString(separator = "\n") {
                " ${it.nameProperty.value}: ${it.scoreProperty.value} / ${it.gamesWonProperty.value}"
            }
        },
        *GameController.players.map { it.nameProperty }.toTypedArray(),
        *GameController.players.map { it.scoreProperty }.toTypedArray(),
        *GameController.players.map { it.gamesWonProperty }.toTypedArray()
    )

    private val scorePane = AnchorPane(

        Label().apply {
            textFill = Color.BLACK
            style = "-fx-background-color: rgba(255,255,255,0.66); -fx-padding: 6 6 6 6; -fx-font-size: 17.0;"
            textProperty().bind(scoreTextProperty)
            AnchorPane.setTopAnchor(this, 0.0)
            AnchorPane.setLeftAnchor(this, 0.0)
        }
    )

    private val cursorsPane = Pane().apply { children += GameController.players.map { Cursor(it.controller) } }


    val view = StackPane(cursorsPane, scorePane)
}



