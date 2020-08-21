package com.yogesh.imgurdemo.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image(
    @PrimaryKey
    val id: String,
    var title: String,
    val description: String?,
    val link: String
)