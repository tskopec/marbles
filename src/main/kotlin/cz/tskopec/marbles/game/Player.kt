package cz.tskopec.marbles.game

import cz.tskopec.marbles.game.control.PlayerController
import cz.tskopec.marbles.game.map.objects.Marble
import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.beans.property.StringProperty
import javafx.collections.ObservableList
import javafx.scene.paint.Color

class Player(id: Int, color: Color, name: String = "Player ${id + 1}") {

	val controller = PlayerController()
	val marbles: ObservableList<Marble> = controller.marbles

	val nameProperty: StringProperty = SimpleStringProperty(name)
	val colorProperty = SimpleObjectProperty(color)

	val scoreProperty = SimpleIntegerProperty(0)
	var gamesWonProperty = SimpleIntegerProperty(0)


	fun clearCurrentGameScore(){
		scoreProperty.set(0)
	}
	fun clearAllScores(){
		scoreProperty.set(0)
		gamesWonProperty.set(0)
	}

}