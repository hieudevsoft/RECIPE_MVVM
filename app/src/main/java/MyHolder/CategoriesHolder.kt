package MyHolder

import Interface.OnRecipeListener
import android.provider.SyncStateContract
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mvvm.project.R
import com.mvvm.project.Retrofit.Recipe
import com.mvvm.project.Utils.Constants

class CategoriesHolder(itemView:View,private var mOnRecipeListener: OnRecipeListener):RecyclerView.ViewHolder(itemView),View.OnClickListener{
        init {
            itemView.setOnClickListener(this)
        }

        fun setData(recipe: Recipe){
                val requestOptions = RequestOptions().centerCrop().placeholder(R.drawable.image_load).error(R.drawable.ic_launcher_background);
                val uriPathImage = "android.resource://com.mvvm.project/drawable/${recipe.image_url}"
                Glide.with(itemView).setDefaultRequestOptions(requestOptions).load(uriPathImage).into(itemView.findViewById(R.id.category_image))
                itemView.findViewById<TextView>(R.id.category_title).text = recipe.title
        }

    override fun onClick(v: View?) {
        mOnRecipeListener.onRecipeCategoryClick(itemView.findViewById<TextView>(R.id.category_title).text.toString())
    }
}