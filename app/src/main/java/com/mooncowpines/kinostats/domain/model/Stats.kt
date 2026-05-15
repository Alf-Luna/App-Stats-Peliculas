package com.mooncowpines.kinostats.domain.model

data class UserStats(
    val yearlyWatchData: List<Pair<String, Float>>,
    val genres: List<StatItem<String, Int>>,
    val countries: List<StatItem<String, Int>>,
    val topActors: List<StatItem<String, Int>>,
    val topDirectors: List<StatItem<String, Int>>,
    val ratings: List<StatItem<Float, Int>>,
    val decades: List<StatItem<Int, Int>>
)

data class StatItem<T, V>(
    val label: T,
    val value: V
)