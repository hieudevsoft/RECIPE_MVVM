package com.mvvm.project.Retrofit.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mvvm.project.Retrofit.Recipe

class RecipeSearchResponse {
    @SerializedName("count")
    @Expose
    private var count:Int = 0

    @SerializedName("recipes")
    @Expose
    private lateinit var recipes:List<Recipe>

    fun getCount():Int{
        return count
    }

    fun getRecipes():List<Recipe>{
        return recipes
    }

    override fun toString(): String {
        return "RecipeSearchResponse(count=$count, recipes=$recipes)"
    }

}