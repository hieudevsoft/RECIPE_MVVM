package com.mvvm.project.Activity


import Adapter.RecipeAdapter
import Interface.OnRecipeListener
import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.project.R
import com.mvvm.project.ViewModels.RecipeListViewModel


class Recipe_List_Activity : BaseActivity(),OnRecipeListener {
    private val TAG = "BaseActivity"
    private lateinit var recipeListViewModel: RecipeListViewModel
    lateinit var adapter:RecipeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_list_activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        subscribeObservers()
        initRecyclerView()
        initSearchView()

    }
    private fun subscribeObservers(){
        recipeListViewModel.getRecipes.observe(this, Observer {
            it?.let {
                adapter.setRecipeList(it)
            }

        })
    }

    private fun initSearchView(){
        val searchView:androidx.appcompat.widget.SearchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object:androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                recipeListViewModel.searchRecipeApi(query,1)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    @SuppressLint("CutPasteId")
    private fun initRecyclerView(){
        adapter = RecipeAdapter(this,this)
        findViewById<RecyclerView>(R.id.recyclerViewList).adapter = adapter
        findViewById<RecyclerView>(R.id.recyclerViewList).layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onRecipeClick(position: Int) {

    }

    override fun onRecipeCategoryClick(category: String) {

    }
}
