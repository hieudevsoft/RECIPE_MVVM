package com.mvvm.project.ViewModels


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mvvm.project.Repository.RecipeRepository


class RecipeListViewModel: ViewModel() {
    var  recipeRepository:RecipeRepository = RecipeRepository.instance
    var isViewingRecipes = false
    var isPerformingQuery = false
    var didRetrieveListRecipe = false
    var getRecipes = recipeRepository.getRecipes()
    fun searchRecipeApi(query: String?, page:Int){
        isViewingRecipes = true
        isPerformingQuery = true
        recipeRepository.searchRecipeApi(query,page)
    }
    fun oBackPress():Boolean{
        if(isPerformingQuery){
            recipeRepository.cancelRequest()
            isPerformingQuery = false
        }
        if(isViewingRecipes){
            isViewingRecipes = false
            return false
        }
        return true
    }
    fun searchNextPage(){
        if(!isPerformingQuery&&isViewingRecipes) recipeRepository.searchNextPage()
    }
    fun getRequestListRecipeTimeout():LiveData<Boolean> = recipeRepository.getRequestListRecipeTimeout()
}