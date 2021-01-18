package com.mvvm.project.Repository

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.mvvm.project.Retrofit.Recipe
import com.mvvm.project.Retrofit.RecipeApiClient

class RecipeRepository private constructor() {
    private val recipeApiClient = RecipeApiClient.instance
    private var currentPage = 0;
    private var currentQuery = "";
    fun getRecipes():LiveData<MutableList<Recipe>?>{
        return recipeApiClient.getRecipes()
    }
    fun getRecipe():LiveData<Recipe>{
        return recipeApiClient.getRecipe()
    }
    fun searchRecipeApi(query: String?, page:Int){
        var pageNumber = page
        if(pageNumber==0) pageNumber = 1
        currentPage = page
        if (query != null) {
            currentQuery= query
        }
        recipeApiClient.searchRecipesApi(currentQuery,currentPage)
    }

    fun getRecipeApi(id:String):Boolean{
        return if(id.isNotBlank()||id.isNotBlank()){
            recipeApiClient.getRecipeApi(id)
            true
        }else {
            false
        }

    }

    fun getRequestRecipeTimeout():LiveData<Boolean> = recipeApiClient.getRequestRecipeTimeout()

    private object Holder {
        val INSTANCE = RecipeRepository()
    }
    companion object{
        @JvmStatic
        val instance:RecipeRepository by lazy { Holder.INSTANCE }
    }

    fun searchNextPage(){
        recipeApiClient.searchRecipesApi(currentQuery,currentPage+1)
    }

    fun cancelRequest(){
        recipeApiClient.cancelRequest()
    }
}