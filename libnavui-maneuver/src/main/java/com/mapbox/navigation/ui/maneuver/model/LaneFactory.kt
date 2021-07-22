package com.mapbox.navigation.ui.maneuver.model

import com.mapbox.navigation.base.ExperimentalMapboxNavigationAPI

/**
 * A factory exposed to build a [Lane] object.
 */
@ExperimentalMapboxNavigationAPI
object LaneFactory {

    /**
     * Build [Lane] given appropriate arguments
     */
    fun buildLane(
        allLanes: List<LaneIndicator>,
        activeDirection: String? = null
    ) = Lane(allLanes, activeDirection)
}
