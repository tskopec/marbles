package cz.tskopec.marbles

import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.Loop
import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.input.KeyHandler
import cz.tskopec.marbles.game.input.MouseHandler
import cz.tskopec.marbles.view.game.buildGameView
import cz.tskopec.marbles.view.game.winnerViewScene
import cz.tskopec.marbles.view.settings.buildSettingsView
import javafx.application.Platform
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.stage.Stage

object AppController {


    private val gameScene = Scene(buildGameView()).apply {
        addEventHandler(KeyEvent.ANY, KeyHandler)
        addEventHandler(MouseEvent.ANY, MouseHandler(this.root))
    }
    private val settingsScene = Scene(buildSettingsView())

    private lateinit var stage: Stage


    fun setStage(stg: Stage) {
        stage = stg
    }


    fun showSettings() {
        Loop.stop()
        with(stage) {
            scene = settingsScene
            title = "Settings"
            sizeToScene()
        }
    }

    fun showGame() {
        if(!GameController.gameInProgress)
            GameController.initializeGame()
        with(stage) {
            scene = gameScene
            title = "Marbles"
            sizeToScene()
        }
        Loop.start()
    }

    fun showWinner(player: Player){
        with(stage){
            scene = winnerViewScene(player)
            sizeToScene()
        }
    }

    fun exitApp() {

        if(confirm("Quit game?"))
            Platform.exit()
    }



    fun confirm(msg: String): Boolean
        = Alert(Alert.AlertType.CONFIRMATION, msg, ButtonType.OK, ButtonType.CANCEL)
            .showAndWait()
            .filter(ButtonType.OK::equals)
            .isPresent
}