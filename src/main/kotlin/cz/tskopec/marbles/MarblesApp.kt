package cz.tskopec.marbles

import javafx.application.Application
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