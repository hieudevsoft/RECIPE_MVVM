package com.mvvm.project.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mvvm.project.Repository.RecipeRepository
import com.mvvm.project.Retrofit.Recipe

class RecipeViewModel : ViewModel() {
    private var recipeRepository = RecipeRepository.instance
    var didRetrieveRecipe = false
    fun getRecipe(): LiveData<Recipe> {
        return recipeRepository.getRecipe()
    }
    fun getRecipeApi(id:String):Boolean{
        return if(id.isNotBlank()||id.isNotBlank()){
            recipeRepository.getRecipeApi(id)
            true
        }else {
            false
        }
    }
    fun getRequestRecipeTimeOut():LiveData<Boolean> = recipeRepository.getRequestRecipeTimeout()
}