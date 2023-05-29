package cz.tskopec.marbles.game.map.build

import cz.tskopec.marbles.game.control.GameController
import cz.tskopec.marbles.game.Settings
import cz.tskopec.marbles.game.map.GameMap
import cz.tskopec.marbles.game.map.objects.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.index.quadtree.Quadtree
import org.locationtech.jts.shape.random.RandomPointsBuilder


	fun spawnMarbles(map: GameMap): List<Marble>
		= spawnRoundObjects(Settings.marblesPerPlayerProperty.get() * 2, map).mapIndexed{ i, baseObj ->
			Marble(baseObj, GameController.players[i % 2])
		}

	fun spawnHoles(map: GameMap): List<Hole>
		= spawnRoundObjects(Settings.nHolesProperty.get(), map).map { Hole(it) }

	fun spawnRoundObjects(n: Int, map: GameMap): List<RoundObject>
		= spawnObjects(n, map.scheme.emptySpace, map.obstacles){ RoundObject(it) }


	fun <T: Collideable> spawnObjects(nObjects: Int, area: Geometry, obstacles: Quadtree, spawn: (Coordinate) -> T): List<T> {

		val objects = mutableListOf<T>()
		while(objects.size < nObjects){

			val points = getPointsBuilder( area, nObjects - objects.size)
			for(coordinate in points.geometry.coordinates){
				val obj = spawn.invoke(coordinate)
				if(obj.notOverlappingAnyOf(obstacles) && obj.notOverlappingAnyOf(objects)){
					objects += obj
				}
			}
		}
		return objects
	}

	private fun getPointsBuilder(extent: Geometry, nPoints: Int)
		= RandomPointsBuilder().apply {
		setExtent(extent)
		setNumPoints(nPoints)
	}
