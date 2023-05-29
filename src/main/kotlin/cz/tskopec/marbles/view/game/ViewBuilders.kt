package cz.tskopec.marbles.view.game

import cz.tskopec.marbles.AppController
import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.util.randomColor
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.StackPane
import javafx.scene.text.TextAlignment


fun buildGameView() = StackPane(
    MapDisplay.view,
    UIDisplay.view,
)


fun buildWinnerView(player: Player) = Label("${player.nameProperty.get()} has won!\nPress any key to continue").apply{
    alignment = Pos.CENTER
    background = Background(BackgroundFill(player.colorProperty.get(), null, null))
    textAlignment = TextAlignment.CENTER
    textFill = player.colorProperty.get().invert()
    style = "-fx-font-size: 42.0"
    maxWidth = Double.MAX_VALUE
    maxHeight = Double.MAX_VALUE
}



fun winnerViewScene(player: Player): Scene = Scene(buildWinnerView(player), Settings.mapWidth, Settings.mapHeight).apply{
    addEventHandler(KeyEvent.KEY_TYPED){
        Settings.randomizeMapColors()
        AppController.showSettings()
    }
}
