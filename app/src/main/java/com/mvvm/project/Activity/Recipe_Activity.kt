package com.mvvm.project.Activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Recipe
import com.mvvm.project.ViewModels.RecipeViewModel


class Recipe_Activity : BaseActivity() {
    private var mRecipeImage: AppCompatImageView? = null
    private var mRecipeTitle: TextView? = null
    private  var mRecipeRank:TextView? = null
    private var mRecipeIngredientsContainer: LinearLayout? = null
    private var mScrollView: ScrollView? = null
    private var recipe:Recipe?=null
    lateinit var recipeModel:RecipeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        mRecipeImage = findViewById(R.id.recipe_image)
        mRecipeTitle = findViewById(R.id.recipe_title)
        mRecipeRank = findViewById(R.id.recipe_social_score)
        mRecipeIngredientsContainer = findViewById(R.id.ingredients_container)
        mScrollView = findViewById(R.id.parent)
        recipeModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        showProgressBar(true)
        subscribeObservers()
        getIncomingIntent()

    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("recipe")) {
            recipe = intent.getParcelableExtra("recipe")
            recipe?.recipe_id?.let { recipeModel.getRecipeApi(it) }
        }
    }
    private fun subscribeObservers() {
        recipeModel.getRecipe().observe(this,
            { recipe ->
                if (recipe != null) {
                    if (recipe.recipe_id == recipeModel.getRecipe().value?.recipe_id) {
                        recipeModel.didRetrieveRecipe = true
                        setRecipeProperties(recipe)
                    }
                }
            })
        recipeModel.getRequestRecipeTimeOut().observe(this, { value ->
            if (value && !recipeModel.didRetrieveRecipe) {
                Toast.makeText(applicationContext, "Request TimeOut", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun setRecipeProperties(recipe: Recipe?) {
        if (recipe != null) {
            val requestOptions: RequestOptions = RequestOptions().centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
            Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.image_url)
                .into(mRecipeImage!!)
            mRecipeTitle!!.text = recipe.title
            mRecipeRank!!.text = Math.round(recipe.social_rank).toString()
            mRecipeIngredientsContainer!!.removeAllViews()
            for (ingredient in recipe.ingredients!!) {
                val textView = TextView(this)
                textView.text = ingredient
                textView.textSize = 15f
                textView.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
                mRecipeIngredientsContainer!!.addView(textView)
            }
        }
        showParent()
        showProgressBar(false)
    }

    private fun showParent() {
        mScrollView!!.visibility = View.VISIBLE
    }
}