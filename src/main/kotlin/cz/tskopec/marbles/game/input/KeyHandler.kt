package cz.tskopec.marbles.game.input

import cz.tskopec.marbles.game.control.GameController
import javafx.application.Platform
import javafx.event.EventHandler
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent

object KeyHandler: EventHandler<KeyEvent> {


	val controls: MutableMap<KeyCode, Action> = mutableMapOf(
		KeyCode.A to TurnLeftAction(GameController.players[0]),
		KeyCode.D to TurnRightAction(GameController.players[0]),
		KeyCode.S to StrikeAction(GameController.players[0]),
		KeyCode.Y to SelectPreviousAction(GameController.players[0]),
		KeyCode.X to SelectNextAction(GameController.players[0]),
		KeyCode.I to TurnLeftAction(GameController.players[1]),
		KeyCode.P to TurnRightAction(GameController.players[1]),
		KeyCode.O to StrikeAction(GameController.players[1]),
		KeyCode.N to SelectPreviousAction(GameController.players[1]),
		KeyCode.M to SelectNextAction(GameController.players[1]),
	)


	override fun handle(event: KeyEvent?) {

		event?.let {

			val action = controls[event.code] ?: return
			val controller = action.owner.controller
			when(event.eventType){
				KeyEvent.KEY_PRESSED -> {
					if(action is Action.Continuing)
						controller.continuousActions += action
				}
				KeyEvent.KEY_RELEASED -> {
					if(action is Action.Continuing)
						controller.continuousActions -= action
					if(action is Action.Instant)
						controller.instantActions += action
				}
			}
		}

	}
}