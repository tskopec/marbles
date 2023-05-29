package cz.tskopec.marbles.view.settings

import cz.tskopec.marbles.AppController
import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.Settings
import javafx.geometry.Insets
import javafx.scene.control.*
import javafx.scene.layout.GridPane
import javafx.scene.layout.HBox


fun buildSettingsView() = HBox().apply {

    children += gameSettingsView()
    GameController.players.forEach { children += buildPlayerSettingsView(it) }
}


private fun gameSettingsView() = GridPane().apply {

    padding = Insets(10.0)
    vgap = 10.0
    hgap = 10.0

    addRow(0, *doubleInput("Map width", Settings.widthProperty, min = 100.0))
    addRow(1, *doubleInput("Map height", Settings.heightProperty, min = 100.0))
    addRow(2, *sliderInput("Friction", Settings.frictionProperty, max = 5.0))
    addRow(3, *sliderInput("Map smoothness", Settings.smoothnessProperty))
    addRow(4, *sliderInput("Map density", Settings.densityProperty))
    addRow(5, *sliderInput("Max strength", Settings.maxStrengthProperty, min = 100.0, max = 500.0))
    addRow(6, *countInput("Balls per player", Settings.ballsPerPlayerProperty))
    addRow(7, *countInput("Number of holes", Settings.nHolesProperty))
    addRow(8, *colorInput("Background color", Settings.backgroundColorProperty))
    addRow(9, *colorInput("Wall color", Settings.obstacleColorProperty))
    addRow(10, *colorInput("Cursor color", Settings.cursorColorProperty))
    addRow(11, buttons())
}


private fun buttons() = HBox(
    Button("Done").apply {
        setOnAction { AppController.showGame() }
    }, Button("Quit").apply {
        setOnAction { AppController.exitApp() }
    }
).apply {
    spacing = 20.0
}


