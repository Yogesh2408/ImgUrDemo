package com.yogesh.imgurdemo

import NetworkConnectionInterceptor
import android.app.Application
import androidx.room.Room
import com.yogesh.imgurdemo.database.ImageDatabase
import com.yogesh.imgurdemo.networks.GalleryRepository
import com.yogesh.imgurdemo.networks.ImgurRestApi
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ImgurApplication : Application(), KodeinAware {

    companion object {
        var database: ImageDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            ImageDatabase::class.java, "image_database"
        )
            .fallbackToDestructiveMigration().build()
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@ImgurApplication))

        bind<ImgurRestApi>() with singleton { ImgurRestApi() }
        bind<GalleryRepository>() with singleton { GalleryRepository() }
        bind<NetworkConnectionInterceptor>() with singleton { NetworkConnectionInterceptor(instance()) }
    }
}