package com.monzo.androidtest.core.di.providers

interface DataPersister<T> : DataProvider<T>{
    fun persistData(data : T)
}