package cz.tskopec.marbles

import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.view.settings.buildSettingsView
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

class MarblesApp : Application() {
	override fun start(stage: Stage) {

		AppController.setStage(stage)
		AppController.showSettings()
		stage.show()
	}
}

fun main() {
	Application.launch(MarblesApp::class.java)
}