package com.mvvm.project.Activity


import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Responses.RecipeResponse
import com.mvvm.project.Retrofit.RetrofitInstance
import com.mvvm.project.ViewModels.RecipeListViewModel
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.Dispatcher
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class Recipe_List_Activity : BaseActivity() {
    private val TAG = "BaseActivity"
    private lateinit var recipeListViewModel: RecipeListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_list_activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        subscribeObservers()
        findViewById<Button>(R.id.btnClick).setOnClickListener {
            recipeListViewModel.searchRecipeApi("chicken",1)
        }
    }
    private fun subscribeObservers(){
        recipeListViewModel.getRecipes.observe(this, Observer {
            if (it != null) {
                for (item in it) Log.d(TAG, "subscribeObservers: ${item.title}")
            }
        })
    }
}
