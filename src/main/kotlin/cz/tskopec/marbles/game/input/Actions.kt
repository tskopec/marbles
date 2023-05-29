package cz.tskopec.marbles.game.input

import cz.tskopec.marbles.game.Player


abstract class Action(val owner: Player) {
    
    interface Continuing {
        fun continuousAction(elapsedSeconds: Double)
    }

    interface Instant {
        fun instantAction()
    }

    abstract val description: String
}

abstract class TurnAction(player: Player, private val direction: Int) : Action(player), Action.Continuing {

    override fun continuousAction(elapsedSeconds: Double) {
        owner.controller.turnCue(direction, elapsedSeconds)
    }
}

class TurnLeftAction(player: Player) : TurnAction(player, 1) {
    override val description: String = "Turn left"
}

class TurnRightAction(player: Player) : TurnAction(player, -1) {
    override val description: String = "Turn right"
}


class StrikeAction(player: Player) : Action(player), Action.Continuing, Action.Instant {

    override fun continuousAction(elapsedSeconds: Double) {
        owner.controller.incrementStrength(elapsedSeconds)
    }

    override fun instantAction() {
        owner.controller.strikeMarble()
    }

    override val description: String = "Strike ball"
}

abstract class SelectBallAction(player: Player, private val direction: Int) : Action(player), Action.Instant {

    override fun instantAction() {
        this.owner.controller.changeSelectedMarble(direction)
    }
}

class SelectNextAction(player: Player) : SelectBallAction(player, 1) {
    override val description: String = "Next ball"
}

class SelectPreviousAction(player: Player) : SelectBallAction(player, -1) {
    override val description: String = "Previous ball"
}


