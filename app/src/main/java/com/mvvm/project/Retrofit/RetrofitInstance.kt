package com.mvvm.project.Retrofit

import com.mvvm.project.Utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class RetrofitInstance{
    companion object{
        private val instance: Retrofit by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
        }

        private val api = instance.create(RetrofitApi::class.java)

        public val getApi = api
    }



}