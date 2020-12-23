package Adapter

import Interface.OnRecipeListener
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Recipe
import kotlin.math.roundToInt

class RecipeAdapter(private var context: Context,private var mOnRecipeListener: OnRecipeListener) :
    RecyclerView.Adapter<RecipeAdapter.MyViewHolder>() {
    private var listRecipe:List<Recipe> = emptyList()
    inner class MyViewHolder(itemView: View,private var mOnRecipeListener: OnRecipeListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        init {
            itemView.setOnClickListener(this as View.OnClickListener)
        }
        fun setData(recipe:Recipe){
            val requestOptions = RequestOptions().placeholder(R.drawable.image_load)
            Glide.with(itemView.context)
                .setDefaultRequestOptions(requestOptions)
                .load(recipe.image_url)
                .into(itemView.findViewById(R.id.recipe_image))
            itemView.findViewById<TextView>(R.id.recipe_title).text = recipe.title
            itemView.findViewById<TextView>(R.id.recipe_publisher).text = recipe.publisher
            itemView.findViewById<TextView>(R.id.recipe_social_score).text = recipe.social_rank.roundToInt()
                .toString()
        }

        override fun onClick(v: View?) {
            mOnRecipeListener.onRecipeClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_recipe_list_item,parent,false)
        return MyViewHolder(view,mOnRecipeListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listRecipe[position]
        item.let { holder.setData(it) }
    }

    override fun getItemCount(): Int {
            return listRecipe?.size!!
    }
    fun setRecipeList(list:List<Recipe>){
        listRecipe = list
        notifyDataSetChanged()
    }
}