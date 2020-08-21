package com.yogesh.imgurdemo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogesh.imgurdemo.model.Image
import com.yogesh.imgurdemo.model.ImageResponse
import com.yogesh.imgurdemo.networks.GalleryRepository
import com.yogesh.imgurdemo.networks.ImgurRestApi


class MainActivityViewModel() : ViewModel() {

    private val repository: GalleryRepository

    val imgId = MutableLiveData<String>()

    fun data(item: String) {
        imgId.value = item
    }

    init {
        repository = GalleryRepository()
    }

    fun getImagesFromViewModel(
        imgurRestApi: ImgurRestApi,
        imageString: String
    ): MutableLiveData<MutableList<ImageResponse?>> {
        return repository.getAllImagesRepo(imgurRestApi, imageString)
    }

    fun getImages(title: String): LiveData<List<Image>> {
        return repository.getImagesFromDB(title)
    }

    fun getImageDetails(imgId: String?): LiveData<Image> {
        return repository.getImageDetailsFromDB(imgId)
    }

    fun setDescription(imgId: String, desc: String) {
        return repository.setDescriptionFromDB(imgId, desc)
    }
}