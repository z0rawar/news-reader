package com.monzo.androidtest.core.di.providers

interface DataProvider<T> {

    fun requestData(callback: (item: T) -> Unit)

    fun requestData(id:String, callback: (item: T) -> Unit): Unit = throw NotImplementedError()

}