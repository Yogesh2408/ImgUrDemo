package com.yogesh.imgurdemo.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogesh.imgurdemo.model.Image

@Dao
interface ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertImages(imageList: List<Image>)

    @Query("SELECT * FROM Image WHERE title LIKE '%' || :title || '%'")
    fun getImages(title: String): LiveData<List<Image>>

    @Query("DELETE FROM Image")
    fun deleteImages()

    @Query("SELECT * FROM Image WHERE id = :imageId")
    fun getImageDetails(imageId: String?): LiveData<Image>

    @Query("UPDATE Image SET description = :desc, edited = :editedTrue WHERE id = :imageId")
    fun setImageDescription(imageId: String, desc: String, editedTrue: Boolean)
}