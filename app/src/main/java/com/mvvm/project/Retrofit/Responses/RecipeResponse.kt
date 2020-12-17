package com.mvvm.project.Retrofit.Responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.mvvm.project.Retrofit.Recipe

class RecipeResponse{
   @SerializedName("recipe")
   @Expose
   private lateinit var recipe:Recipe
   public fun getRecipe():Recipe{ return recipe;}
    override fun toString(): String {
        return "RecipeResponse(recipe=$recipe)"
    }
}