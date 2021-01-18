package Adapter

import Interface.OnRecipeListener
import MyHolder.CategoriesHolder
import MyHolder.LoadingHolder
import MyHolder.RecipeHolder
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Recipe
import com.mvvm.project.Utils.Constants


class RecipeAdapter(private var context: Context, private var mOnRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val RECIPE_TYPE = 1;
    private val LOADING_TYPE = 2;
    private val CATEGORY_TYPE = 3;
    private var listRecipe:List<Recipe> = emptyList()

    override fun getItemCount(): Int {
            return listRecipe.size
    }

    fun setRecipeList(list: List<Recipe>){
        listRecipe = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val type = getItemViewType(position)
        if(type==RECIPE_TYPE){
            val item = listRecipe[position]
            (holder as RecipeHolder).setData(item)
        }
        if(type==CATEGORY_TYPE){
            val  item = listRecipe[position]
            (holder as CategoriesHolder).setData(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RECIPE_TYPE -> {
                val view = LayoutInflater.from(context).inflate(
                    R.layout.layout_recipe_list_item,
                    parent,
                    false
                )
                RecipeHolder(view, mOnRecipeListener)
            }
            LOADING_TYPE -> {
                val view = LayoutInflater.from(context).inflate(
                    R.layout.loading_layout,
                    parent,
                    false
                )
                LoadingHolder(view)
            }
            CATEGORY_TYPE -> {
                val view = LayoutInflater.from(context).inflate(
                    R.layout.layout_category_list_item,
                    parent,
                    false
                )
                CategoriesHolder(view, mOnRecipeListener)
            }
            else->{
                val view = LayoutInflater.from(context).inflate(
                    R.layout.layout_recipe_list_item,
                    parent,
                    false
                )
                RecipeHolder(view, mOnRecipeListener)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(listRecipe[position].social_rank == -1f){
            return CATEGORY_TYPE;
        }
        else if(listRecipe[position].title.equals("loading")){
            return LOADING_TYPE;
        }
        else{
            return RECIPE_TYPE;
        }
    }

    private fun isLoading():Boolean{
        if(listRecipe.isNotEmpty()){
            return listRecipe[listRecipe.size - 1].title=="loading"
        }
        return false
    }

     fun displayLoading(){
        if(!isLoading()){
            val recipe = Recipe("loading", "", null, "", "", 0F)
            val list = ArrayList<Recipe>()
            list.add(recipe)
            listRecipe = list
            notifyDataSetChanged()
        }
    }
    fun displaySearchCategories() {
        val categories: MutableList<Recipe> = ArrayList()
        for (i in Constants.DEFAULT_SEARCH_CATEGORIES.indices) {
            val recipe: Recipe = Recipe("loading", "", null, "", "", 0F)
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
            recipe.image_url = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
            recipe.social_rank = -1f
            categories.add(recipe)
        }
        listRecipe = categories
        notifyDataSetChanged()
    }

    fun getRecipeClick(position:Int): Recipe? {
        listRecipe?.let {
            if(listRecipe.isNotEmpty()) return listRecipe[position]
        }
        return null
    }
}