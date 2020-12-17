package com.mvvm.project.Retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mvvm.project.AppExecutors
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
    fun getRecipes():LiveData<MutableList<Recipe>?> = mRecipes

    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null
    fun searchRecipesApi(query: String, pageNumber: Int) {
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
        private val query: String,
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

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse> {
            return RetrofitInstance.getApi.searchRecipes(
                query, pageNumber.toString()
            )
        }

        private fun cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the retrieval query")
            cancelRequest = true
        }
    }


}