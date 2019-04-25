package com.monzo.androidtest.common

import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


open class BasePresenter<in V : BasePresenterView> {
    private val disposables = CompositeDisposable()
    private var view: V? = null

    @CallSuper
    open fun register(view: V) {
        if (this.view != null) {
            throw IllegalStateException("View " + this.view + " is already attached. Cannot attach " + view)
        }
        this.view = view
    }

    @CallSuper
    fun unregister() {
        if (view == null) {
            throw IllegalStateException("View is already detached")
        }
        view = null
        disposables.clear()
    }

    @CallSuper
    protected fun addToUnsubscribe(disposable: Disposable) {
        disposables.add(disposable)
    }
}
