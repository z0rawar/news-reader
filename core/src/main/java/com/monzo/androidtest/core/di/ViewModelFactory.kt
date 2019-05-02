package com.monzo.androidtest.core.di

import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
 
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = viewModelProviders[modelClass]
            ?: viewModelProviders.entries.first { modelClass.isAssignableFrom(it.key) }.value
 
        return provider.get() as T
    }
}