package com.mvvm.project.Retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.project.AppExecutors
import com.mvvm.project.Retrofit.Responses.RecipeResponse
import com.mvvm.project.Retrofit.Responses.RecipeSearchResponse
import com.mvvm.project.Utils.Constants
import retrofit2.Call
import java.util.*
import java.util.concurrent.TimeUnit


class RecipeApiClient private constructor() {

    private object Holder{
        val INSTANCE = RecipeApiClient()
    }

    companion object {
        private const val TAG = "RecipeApiClient"
        val instance: RecipeApiClient by lazy { Holder.INSTANCE }
    }

    private val mRecipes: MutableLiveData<MutableList<Recipe>?> = MutableLiveData()
    private val mRecipe: MutableLiveData<Recipe> = MutableLiveData()
    private val mRequestRecipeTimeOut: MutableLiveData<Boolean> = MutableLiveData()
    fun getRecipes():LiveData<MutableList<Recipe>?> = mRecipes
    fun getRecipe():LiveData<Recipe> = mRecipe
    fun getRequestRecipeTimeout():LiveData<Boolean> = mRequestRecipeTimeOut

    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null
    private var mRetrieveRecipeRunnable: RetrieveRecipeRunnable? = null
    fun searchRecipesApi(query: String?, pageNumber: Int) {
        if (mRetrieveRecipesRunnable != null) {
            mRetrieveRecipesRunnable = null
        }
        mRetrieveRecipesRunnable = RetrieveRecipesRunnable(query, pageNumber)
        val handler = AppExecutors.instance.getNetWorkIO().submit(mRetrieveRecipesRunnable)

        // Set a timeout for the data refresh
        AppExecutors.instance.getNetWorkIO().schedule(Runnable { // let the user know it timed out
            handler.cancel(true)
        }, Constants.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    }

    private inner class RetrieveRecipesRunnable(
        private val query: String?,
        private val pageNumber: Int
    ) :
        Runnable {
        private var cancelRequest = false
        override fun run() {
            if (cancelRequest) {
                return
            }
            try {
                val response = getRecipes(query, pageNumber).execute()
                if (response.code() == 200) {
                    val list = ArrayList(
                        response.body()!!.getRecipes()
                    )
                    if (pageNumber == 1) {
                        mRecipes.postValue(list)
                    } else {
                        val currentRecipes = mRecipes.value
                        currentRecipes!!.addAll(list)
                        mRecipes.postValue(currentRecipes)
                    }
                } else {
                    Log.e(TAG, "run: error: ${response.errorBody()}")
                    mRecipes.postValue(null)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                mRecipes.postValue(null)
            }
        }

        private fun getRecipes(query: String?, pageNumber: Int): Call<RecipeSearchResponse> {
            return RetrofitInstance.getApi.searchRecipes(
                query, pageNumber.toString()
            )
        }

        fun cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the retrieval query")
            cancelRequest = true
        }
    }

    private inner class RetrieveRecipeRunnable(private var id: String):Runnable{
        private var cancelRequest = false;
        override fun run() {
            if(cancelRequest) return
            try {
                val response = getDetailsRecipe(id).execute()
                if(response.code() == 200){
                    val recipe = response.body()?.getRecipe()
                    mRecipe.postValue(recipe)
                }else{
                    val error = response.errorBody()!!.string()
                    Log.e(TAG, "run: $error")
                    mRecipe.postValue(null)
                }
            }catch (e: Exception){
                e.printStackTrace()
                mRecipe.postValue(null)
            }
        }

        fun getDetailsRecipe(id: String):Call<RecipeResponse>{
            return RetrofitInstance.getApi.getRecipe(id)
        }

        fun cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the retrieval details query")
            cancelRequest=true
        }
    }

    fun getRecipeApi(id:String) {
        if (mRetrieveRecipeRunnable != null) {
            mRetrieveRecipeRunnable = null
        }
        mRetrieveRecipeRunnable = RetrieveRecipeRunnable(id)
        val handler = AppExecutors.instance.getNetWorkIO().submit(mRetrieveRecipeRunnable)
        mRequestRecipeTimeOut.value = false
        // Set a timeout for the data refresh
        AppExecutors.instance.getNetWorkIO().schedule(Runnable {
            mRequestRecipeTimeOut.postValue(true)// let the user know it timed out
            handler.cancel(true)
        }, Constants.CONNECTION_TIMEOUT.toLong(), TimeUnit.SECONDS)
    }

     fun cancelRequest(){
         mRetrieveRecipesRunnable?.let {
             mRetrieveRecipesRunnable!!.cancelRequest()
         }
         mRetrieveRecipeRunnable?.let {
             mRetrieveRecipeRunnable!!.cancelRequest()
         }
    }

}