package com.mooncowpines.kinostats.data.mapper

import com.mooncowpines.kinostats.data.remote.dto.UserDTO
import com.mooncowpines.kinostats.domain.model.User

fun UserDTO.toDomain(): User {
    return User(
        id = this.id,
        userName = this.userName,
        email = this.email,
        pass = this.pass
    )
}