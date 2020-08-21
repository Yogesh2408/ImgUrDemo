package com.yogesh.imgurdemo.networks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yogesh.imgurdemo.ImgurApplication
import com.yogesh.imgurdemo.database.ImageDao
import com.yogesh.imgurdemo.model.Image
import com.yogesh.imgurdemo.model.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GalleryRepository() {

    private var responseList: MutableLiveData<MutableList<ImageResponse?>> = MutableLiveData()

    fun getAllImagesRepo(
        imgurRestApi: ImgurRestApi,
        imageString: String
    ): MutableLiveData<MutableList<ImageResponse?>> {

        imgurRestApi.getAllImages(imageString).enqueue(object : Callback<ImageResponse> {
            override fun onFailure(call: Call<ImageResponse>?, t: Throwable?) {
                Log.e("Response fail", t.toString())
            }

            override fun onResponse(
                call: Call<ImageResponse>?,
                response: Response<ImageResponse>?
            ) {
                println("Response Pass $response!!.body().toString()")
                val dataList = response?.body()?.data ?: ArrayList()
                Thread(Runnable {
                    var count = 0
                    for (data in dataList) {
                        if (!data.images.isNullOrEmpty()) {
                            for (image in data.images) {
                                image.title = data.title
                                count++
                            }
                            println("count  $count")
                            //getDao().deleteImages()
                            getDao().insertImages(data.images)
                        }
                    }
                }).start()
            }
        })
        return responseList
    }

    /**
     * Gets Image dao.
     */
    private fun getDao(): ImageDao {
        return ImgurApplication.database!!.imageDao()
    }

    fun getImagesFromDB(title: String): LiveData<List<Image>> {
        return getDao().getImages(title)
    }

    fun getImageDetailsFromDB(imgId: String?): LiveData<Image> {
        return getDao().getImageDetails(imgId)
    }

    fun setDescriptionFromDB(imgId: String, desc: String) {
        Thread(Runnable {
            getDao().setImageDescription(imgId, desc, true)
        }).start()
    }
}

