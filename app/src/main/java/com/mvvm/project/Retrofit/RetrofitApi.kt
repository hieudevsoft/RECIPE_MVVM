package com.mvvm.project.Retrofit

import com.mvvm.project.Retrofit.Responses.RecipeResponse
import com.mvvm.project.Retrofit.Responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi{
    //SEARCH
    @GET("search")
    fun searchRecipes(
//    @Query("key") key:String,
        @Query("q") query: String?,
        @Query("page") page:String
    ): Call<RecipeSearchResponse>

    @GET("get")
    fun getRecipe(
//        @Query("key") key:String,
        @Query("rId") recipe_Id:String,
    ): Call<RecipeResponse>
}