package com.mvvm.project.Activity


import Adapter.RecipeAdapter
import Interface.OnRecipeListener
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.project.R
import com.mvvm.project.Utils.VerticalSpacingItemDecorator
import com.mvvm.project.ViewModels.RecipeListViewModel


class Recipe_List_Activity : BaseActivity(),OnRecipeListener {
    private val TAG = "BaseActivity"
    private lateinit var recipeListViewModel: RecipeListViewModel
    lateinit var adapter:RecipeAdapter
    lateinit var searchView:androidx.appcompat.widget.SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recipe_list_activity)
        recipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        searchView = findViewById(R.id.searchView)
        subscribeObservers()
        initRecyclerView()
        initSearchView()
        if(!recipeListViewModel.isViewingRecipes){
            displayCategories()
        }
    }
    private fun subscribeObservers(){
        recipeListViewModel.getRecipes.observe(this, Observer {
            it?.let {
                if (recipeListViewModel.isViewingRecipes) {
                    recipeListViewModel.isPerformingQuery = true
                    adapter.setRecipeList(it)
                }

            }

        })
    }

    private fun initSearchView(){

        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                adapter.displayLoading()
                recipeListViewModel.searchRecipeApi(query, 1)
                searchView.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    @SuppressLint("CutPasteId")
    private fun initRecyclerView(){
        adapter = RecipeAdapter(this, this)
        findViewById<RecyclerView>(R.id.recyclerViewList).addItemDecoration(
            VerticalSpacingItemDecorator(
                30
            )
        )
        findViewById<RecyclerView>(R.id.recyclerViewList).adapter = adapter
        findViewById<RecyclerView>(R.id.recyclerViewList).layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        findViewById<RecyclerView>(R.id.recyclerViewList).addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1)) recipeListViewModel.searchNextPage()
            }
        })
    }

    override fun onRecipeClick(position: Int) {
        Toast.makeText(this, "${adapter.getRecipeClick(position)?.title}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this,Recipe_Activity::class.java)
        intent.putExtra("recipe",adapter.getRecipeClick(position)).also {
            startActivity(it)
     }
    }

    override fun onRecipeCategoryClick(category: String) {
        Toast.makeText(this, category, Toast.LENGTH_SHORT).show()
        adapter.displayLoading()
        recipeListViewModel.searchRecipeApi(category, 1)
        searchView.clearFocus()
    }

    private fun displayCategories(){
        recipeListViewModel.isViewingRecipes = false
        adapter.displaySearchCategories()
    }

    override fun onBackPressed() {
        if(recipeListViewModel.oBackPress()){
            super.onBackPressed()
        }else{
            displayCategories()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun menuOnClick(item: MenuItem) {
        displayCategories();
    }
}
