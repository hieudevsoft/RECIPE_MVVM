package com.mvvm.project.ViewModels


import androidx.lifecycle.ViewModel
import com.mvvm.project.Repository.RecipeRepository


class RecipeListViewModel: ViewModel() {
    var  recipeRepository:RecipeRepository = RecipeRepository.instance

    var getRecipes = recipeRepository.getRecipes()
    fun searchRecipeApi(query: String?, page:Int){
        recipeRepository.searchRecipeApi(query,page)
    }
}