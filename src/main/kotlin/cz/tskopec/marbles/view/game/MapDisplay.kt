package cz.tskopec.marbles.view.game

import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.map.GameMap
import cz.tskopec.marbles.game.map.objects.Renderable
import javafx.beans.binding.Bindings
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.scene.Group
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.scene.shape.Rectangle

// provides view pane containing the shapes of all marbles and static map objects
object MapDisplay {

    // all renderable map components must be added to this list by showMap function
    private val terrain: ObservableList<Renderable> = FXCollections.observableArrayList()

    private val mapBackground = Rectangle(0.0, 0.0, Settings.mapWidth, Settings.mapHeight).apply {
        fillProperty().bind(Bindings.createObjectBinding({
            LinearGradient(0.0,0.0, 1.0, 1.0, true, CycleMethod.NO_CYCLE,
                Stop(0.0, Settings.backgroundColorProperty.get().interpolate(Color.WHITE, 0.42)),
                Stop(1.0, Settings.backgroundColorProperty.get().interpolate(Color.BLACK, 0.42))
            )
        }, Settings.backgroundColorProperty))
    }

    val view = Pane().apply {
        children += mapBackground
        children += gameObjects()
        background = Background(BackgroundFill(Color.BLACK, null, null))
    }


    fun showMap(map: GameMap) {
        mapBackground.apply {
            width = Settings.mapWidth
            height = Settings.mapHeight
        }
        terrain.setAll(map.allTerrain())
    }

    // group of shapes to render, keeping its contents in sync with list of static map objects and marbles of all players
    private fun gameObjects() = Group().apply {

        val contentUpdater = ListChangeListener<Renderable> { c ->
            while (c.next()) {
                c.addedSubList.forEach { children += it.shape }
                c.removed.forEach { children -= it.shape }
            }
        }
        val observedLists = listOf(terrain, *GameController.players.map { it.marbles }.toTypedArray())
        observedLists.forEach { it.addListener(contentUpdater) }
    }

}




