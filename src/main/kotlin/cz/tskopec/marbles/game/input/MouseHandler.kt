package cz.tskopec.marbles.game.input

import cz.tskopec.marbles.AppController
import cz.tskopec.marbles.game.control.GameController
import javafx.event.EventHandler
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.MenuItem
import javafx.scene.input.MouseButton
import javafx.scene.input.MouseEvent

class MouseHandler(private val view: Node) : EventHandler<MouseEvent> {

    private val contextMenu = ContextMenu().apply {
        items += MenuItem("New map").apply { setOnAction {
            GameController.endGame()
            AppController.showGame()
        } }
        items += MenuItem("Settings").apply { setOnAction { AppController.showSettings() } }
        items += MenuItem("Clear scores").apply { setOnAction { GameController.clearScores() } }
        items += MenuItem("Exit").apply { setOnAction { AppController.exitApp() } }
    }

    override fun handle(event: MouseEvent?) {

        event?.let {
            when (it.button) {
                MouseButton.PRIMARY -> {
                    if (contextMenu.isShowing)
                        contextMenu.hide()
                }
                MouseButton.SECONDARY -> contextMenu.show(view, event.screenX, event.screenY)
                else -> {}
            }
        }

    }
}