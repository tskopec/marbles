package cz.tskopec.marbles.util

import cz.tskopec.marbles.game.map.objects.Cell
import javafx.scene.paint.Color
import java.util.*
import kotlin.random.Random


fun <T> allPairs(items: Collection<T>) = items.flatMap { e -> items.filter { it != e }.map { e to it } }


fun bfSearch(start: Cell, end: Cell, graph: Map<Cell, Collection<Cell>>): List<Cell> {

    val parents = mutableMapOf<Cell, Cell?>(start to null)
    val queue: Queue<Cell> = LinkedList<Cell>().apply { offer(start) }

    while (queue.isNotEmpty()) {
        val currentNode = queue.poll()
        if (currentNode == end) {
            return generateSequence(currentNode) { parents[it] }.toList()
        } else {
            val neighbourNodes = graph[currentNode] ?: throw IllegalStateException("Isolated node")
            neighbourNodes
                .filter { !parents.containsKey(it) }
                .forEach {
                    parents[it] = currentNode
                    queue.offer(it)
                }
        }
    }
    throw IllegalStateException("No path connecting start and end found in the graph")
}

fun randomColor(): Color = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))
