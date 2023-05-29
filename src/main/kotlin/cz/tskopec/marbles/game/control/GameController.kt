package cz.tskopec.marbles.game.control

import cz.tskopec.marbles.AppController
import cz.tskopec.marbles.game.Loop
import cz.tskopec.marbles.game.Player
import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.input.Action
import cz.tskopec.marbles.game.map.GameMap
import cz.tskopec.marbles.game.map.build.MapFactory
import cz.tskopec.marbles.game.map.build.spawnMarbles
import cz.tskopec.marbles.game.physics.PhysicsHandler
import cz.tskopec.marbles.util.randomColor
import cz.tskopec.marbles.view.game.MapDisplay
import javafx.beans.InvalidationListener

object GameController {

    val players = List(Settings.nPlayers) { Player(it, randomColor()) }.onEach(::addWinningListener)
    var gameInProgress = false

    val continuousActions = mutableSetOf<Action.Continuing>() // actions bound to currently pressed keys
    val instantActions = mutableSetOf<Action.Instant>() // actions bound to keys released since the previous frame
    private lateinit var physics: PhysicsHandler


    fun initializeGame() {

        val map = MapFactory.create()
        physics = PhysicsHandler(map.obstacles)
        MapDisplay.showMap(map)
        distributeMarbles(map)
        players.forEach(Player::clearCurrentGameScore)
        gameInProgress = true
    }

    fun endGame(winner: Player? = null) {

        Loop.stop()
        gameInProgress = false
        winner?.let {
            winner.gamesWonProperty.set(winner.gamesWonProperty.get() + 1)
            AppController.showWinner(winner)
        }
    }


    fun nextFrame(elapsedSeconds: Double) {

        applyActions(elapsedSeconds)
        physics.update(elapsedSeconds)
    }


    fun clearScores() {

        if (AppController.confirm("Clear all scores?"))
            players.forEach(Player::clearAllScores)
    }


    private fun applyActions(elapsedSeconds: Double){
        continuousActions.forEach { it.continuousAction(elapsedSeconds) }
        instantActions.forEach { it.instantAction() }
        instantActions.clear()
    }


    private fun distributeMarbles(map: GameMap) {
        val marbles = spawnMarbles(map)
        players.forEach { player ->
            player.marbles.setAll(marbles.filter { it.owner == player })
        }
    }

    private fun addWinningListener(player: Player) = player.marbles.addListener(InvalidationListener {
        if (player.marbles.isEmpty())
            endGame(winner = player)
    })

}