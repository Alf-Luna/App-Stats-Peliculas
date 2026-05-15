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
    data object Logs  : Route("logs")

    data object ListDetail : Route("list_detail/{listId}") {
        fun createRoute(listId: Long) = "list_detail/$listId"
    }
    data object MovieDetail : Route("movie_detail/{movieId}") {
        fun createRoute(movieId: Long) = "movie_detail/$movieId"
    }

    data object LogDetail : Route("log_detail/{movieId}?logId={logId}") {
        fun createRoute(movieId: Long, logId: Long? = null): String {
            return if (logId != null) {
                "log_detail/$movieId?logId=$logId"
            } else {
                "log_detail/$movieId"
            }
        }
    }

    data object Search : Route("search/{query}") {
        fun createRoute(query: String) = "search/$query"
    }
}
