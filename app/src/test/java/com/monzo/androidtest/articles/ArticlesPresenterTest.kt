package com.monzo.androidtest.articles


import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

import org.mockito.MockitoAnnotations.initMocks

class ArticlesPresenterTest {
    @Mock private lateinit var articlesRepository: ArticlesRepository
    @Mock private lateinit var view: ArticlesPresenter.View

    private lateinit var presenter: ArticlesPresenter

    @Before
    @Throws(Exception::class)
    fun setUp() {
        initMocks(this)

        presenter = ArticlesPresenter(Schedulers.trampoline(), Schedulers.trampoline(),
                articlesRepository)
    }

    @Test
    @Throws(Exception::class)
    fun register() {
        presenter.register(view)

    }
}