package MyHolder

import Interface.OnRecipeListener
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Recipe
import kotlin.math.roundToInt

class RecipeHolder(itemView:View,private var mOnLickListener:OnRecipeListener) : RecyclerView.ViewHolder(itemView),View.OnClickListener{

    init {
        itemView.setOnClickListener(this)
    }
    fun setData(recipe: Recipe){
        val requestOptions = RequestOptions().centerCrop().placeholder(R.drawable.image_load)
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
        mOnLickListener.onRecipeClick(adapterPosition)
    }

}