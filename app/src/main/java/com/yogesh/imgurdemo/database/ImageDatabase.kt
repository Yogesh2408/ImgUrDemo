package com.yogesh.imgurdemo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yogesh.imgurdemo.model.Image
import yogesh.com.shaadiassignment.DB.ImageDao

@Database(entities = arrayOf(Image::class), version = 1, exportSchema = false)
abstract class ImageDatabase() : RoomDatabase() {

    abstract fun imageDao(): ImageDao
//
//    companion object {
//        // Singleton prevents multiple instances of database opening at the
//        // same time.
//        @Volatile
//        private var INSTANCE: ImageDatabase? = null
//
//        fun getDatabase(context: Context): ImageDatabase {
//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ImageDatabase::class.java,
//                    "image_database"
//                ).fallbackToDestructiveMigration().build()
//                INSTANCE = instance
//                return instance
//            }
//        }
//    }
}