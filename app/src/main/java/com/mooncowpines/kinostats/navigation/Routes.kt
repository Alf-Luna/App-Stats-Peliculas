package com.mooncowpines.kinostats.navigation
sealed class Route(val path: String) {
    data object Login    : Route("login")
    data object Register : Route("register")
    data object Recovery : Route("recovery")
    data object Reset   : Route("reset")
    data object Change  : Route("change")
    data object Home     : Route("home")
    data object Profile  : Route("profile")
    data object Stats  : Route("stats")

    data object Lists : Route("lists")

    data object ListDetail : Route("list_detail/{listId}") {
        fun createRoute(listId: Long) = "list_detail/$listId"
    }
    data object MovieDetail : Route("movie_detail/{movieId}") {
        fun createRoute(movieId: Long) = "movie_detail/$movieId"
    }
    data object MovieLog : Route("movie_log/{movieId}") {
        fun createRoute(movieId: Long) = "movie_log/$movieId"
    }

    data object Review : Route("review/{movieId}") {
        fun createRoute(movieId: Long) = "review/$movieId"
    }

    data object Search : Route("search/{query}") {
        fun createRoute(query: String) = "search/$query"
    }
}
