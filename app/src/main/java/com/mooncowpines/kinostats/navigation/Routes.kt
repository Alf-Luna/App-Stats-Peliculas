package com.mooncowpines.kinostats.navigation
sealed class Route(val path: String) {
    data object Home     : Route("home")
    data object Login    : Route("login")
    data object Register : Route("register")
    data object Recovery : Route("recovery")
    data object Change   : Route("Change")
    data object MovieDetail : Route("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
}
