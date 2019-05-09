package com.monzo.androidtest.core.di.providers

import androidx.lifecycle.LiveData

interface DataProvider<T> {

    fun requestData(callback: (item: T) -> Unit)

    fun requestLiveData(callback: (item: LiveData<T>) -> Unit) : Unit = throw NotImplementedError()

    fun requestData(id:String, callback: (item: T) -> Unit): Unit = throw NotImplementedError()

}