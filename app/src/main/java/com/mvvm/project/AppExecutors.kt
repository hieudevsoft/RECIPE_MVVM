package com.mvvm.project

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class AppExecutors {
    private object Holder{
        val INSTANCE = AppExecutors()
    }
    companion object{
        val instance:AppExecutors by lazy { Holder.INSTANCE }
    }

    private val mNetWorkIO = Executors.newScheduledThreadPool(3)

    fun getNetWorkIO():ScheduledExecutorService{
        return mNetWorkIO
    }
}