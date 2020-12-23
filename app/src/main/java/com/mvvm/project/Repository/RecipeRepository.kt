package com.mvvm.project.Repository

import androidx.lifecycle.LiveData
import com.mvvm.project.Retrofit.Recipe
import com.mvvm.project.Retrofit.RecipeApiClient

class RecipeRepository private constructor() {
    private val recipeApiClient = RecipeApiClient.instance

    fun getRecipes():LiveData<MutableList<Recipe>?>{
        return recipeApiClient.getRecipes()
    }

    fun searchRecipeApi(query: String?, page:Int){
        var pageNumber = page
        if(pageNumber==0) pageNumber = 1
        recipeApiClient.searchRecipesApi(query,pageNumber)
    }

    private object Holder {
        val INSTANCE = RecipeRepository()
    }
    companion object{
        @JvmStatic
        val instance:RecipeRepository by lazy { Holder.INSTANCE }
    }
}