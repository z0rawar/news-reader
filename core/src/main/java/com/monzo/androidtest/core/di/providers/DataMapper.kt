package com.monzo.androidtest.core.di.providers

interface DataMapper<S, R> {
    fun encode(source: S) :R
    fun decode(source: R) :S = throw NotImplementedError()
}