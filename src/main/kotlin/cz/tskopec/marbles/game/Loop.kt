package cz.tskopec.marbles.game

import cz.tskopec.marbles.game.control.GameController
import javafx.animation.AnimationTimer

object Loop: AnimationTimer() {

    private var lastFrameTime = 0L

    override fun handle(now: Long) {

        GameController.nextFrame((now - lastFrameTime) / 1_000_000_000.0)
        lastFrameTime = now
    }

    override fun start() {
        lastFrameTime = System.nanoTime()
        super.start()
    }

}