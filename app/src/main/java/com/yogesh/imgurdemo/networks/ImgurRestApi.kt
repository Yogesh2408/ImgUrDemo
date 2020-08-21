package com.yogesh.imgurdemo.networks

import NetworkConnectionInterceptor
import com.google.gson.Gson
import com.yogesh.imgurdemo.model.ImageResponse
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ImgurRestApi {

    @GET("gallery/search/1?")
    fun getAllImages(@Query("q") imageString: String): Call<ImageResponse>

    companion object {
        val BASE_URL: String
            get() = "https://api.imgur.com/3/"

        operator fun invoke(): ImgurRestApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val request = chain
                        .request().newBuilder()
                        .header("Authorization", "Client-ID 137cda6b5008a7c")
                        .build()
                    chain.proceed(request)
                }.build()


            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .build()
                .create(ImgurRestApi::class.java)
        }
    }
}