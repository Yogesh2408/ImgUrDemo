package com.yogesh.imgurdemo.model

data class ImageResponse(
    val data: List<Data>,
    val status: Double,
    val success: Boolean
) {
    override fun toString(): String {
        return "ImageResponse {success=  $success, status= $status, data=$data }"
    }
}