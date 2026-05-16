package com.mooncowpines.kinostats.domain.repository

import com.mooncowpines.kinostats.domain.model.Home

interface HomeRepository {
    suspend fun getHomeData(userId: Long): Home?
}