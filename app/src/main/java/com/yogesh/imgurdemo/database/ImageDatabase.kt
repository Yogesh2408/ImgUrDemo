package com.yogesh.imgurdemo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yogesh.imgurdemo.model.Image

@Database(entities = arrayOf(Image::class), version = 1, exportSchema = false)
abstract class ImageDatabase() : RoomDatabase() {

    abstract fun imageDao(): ImageDao
}