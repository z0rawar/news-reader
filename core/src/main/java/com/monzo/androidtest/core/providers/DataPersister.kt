package com.monzo.androidtest.core.providers

interface DataPersister<T> : DataProvider<T> {
    fun insertData(data: T)
    fun updateData(data: T)

}