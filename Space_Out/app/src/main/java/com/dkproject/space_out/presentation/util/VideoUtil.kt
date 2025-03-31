package com.dkproject.space_out.presentation.util

import com.dkproject.space_out.R
import com.dkproject.space_out.presentation.model.VideoInfo

object VideoUtil {
    private const val FIRE = "video_fire"
    private const val FOREST = "video_forest"
    private const val OCEAN = "video_ocean"
    private const val STAR = "video_star"
    private const val SUN = "video_sun"
    private const val TREE = "video_tree"
    private const val UNIVERSE = "video_universe"
    private const val SNOW = "video_snow"
    fun getVideoDataList(): List<VideoInfo> {
        return listOf(
            VideoInfo(thumbnail = R.drawable.thum_fire, videoName = R.string.fire, videoPath = FIRE),
            VideoInfo(thumbnail = R.drawable.thum_ocean, videoName = R.string.ocean, videoPath = OCEAN),
            VideoInfo(thumbnail = R.drawable.thum_forest, videoName = R.string.forest, videoPath = FOREST),
            VideoInfo(thumbnail = R.drawable.thum_tree, videoName = R.string.tree, videoPath = TREE),
            VideoInfo(thumbnail = R.drawable.thum_star, videoName = R.string.star, videoPath = STAR),
            VideoInfo(thumbnail = R.drawable.thum_universe, videoName = R.string.universe, videoPath = UNIVERSE),
            VideoInfo(thumbnail = R.drawable.thum_sun, videoName = R.string.sun, videoPath = SUN),
            VideoInfo(thumbnail = R.drawable.thum_snow, videoName = R.string.snow, videoPath = SNOW),
            )
    }
}