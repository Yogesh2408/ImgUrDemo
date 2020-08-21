package yogesh.com.shaadiassignment.DB

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

    @Query("SELECT * FROM Image")
    fun getImages(): LiveData<List<Image>>

    @Query("DELETE FROM Image")
    fun deleteImages()

    @Query("SELECT * FROM Image WHERE id = :imageId")
    fun getImageDetails(imageId: String?): LiveData<Image>

    @Query("UPDATE Image SET description = :desc WHERE id = :imageId")
    fun setImageDescription(imageId: String, desc: String)
}