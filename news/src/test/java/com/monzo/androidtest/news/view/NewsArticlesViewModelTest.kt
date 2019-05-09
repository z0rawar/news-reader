package com.monzo.androidtest.news.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.monzo.androidtest.core.di.providers.DataProvider
import com.monzo.androidtest.news.api.Article
import com.monzo.androidtest.news.entities.NewsArticlesState
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.*

class NewsArticlesViewModelTest {

    @Rule
    @JvmField
    val testLiveDataRule = InstantTaskExecutorRule()

    lateinit var viewModel: NewsArticlesViewModel

    @Mock
    lateinit var newsArticleRepo: DataProvider<NewsArticlesState>

    val listOfArticles = listOf(Article(id = "001",
            thumbnail = "",
            sectionId = "",
            published = Date(),
            favourite = true,
            url = "url",
            title = "Title",
            sectionName = "",
            body = ""))

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = NewsArticlesViewModel(newsArticleRepo, Unconfined, Unconfined)
        viewModel.newsArticlesViewState.observeForever(mock(Observer::class.java) as Observer<NewsArticlesViewState>)
    }

    @Test
    fun `when list is refreshed - is loading - should show loader `() {
        whenever(newsArticleRepo.requestData(any())).thenAnswer {
            val callback = it.getArgument<(NewsArticlesState) -> Unit>(0)
            callback.invoke(NewsArticlesState.Loading)
        }

        runBlocking {
            viewModel.onListRefreshed()
        }

        assertEquals(NewsArticlesViewState.InProgress, viewModel.newsArticlesViewState.value)
    }

    @Test
    fun `when list is refreshed - articles list with 1 fav fetched - should show article with Fav header`() {
        whenever(newsArticleRepo.requestData(any())).thenAnswer {
            val callback = it.getArgument<(NewsArticlesState) -> Unit>(0)
            callback.invoke(NewsArticlesState.Success(listOfArticles))
        }

        runBlocking {
            viewModel.onListRefreshed()
        }

        val value = LiveDataTestUtil.getValue(viewModel.newsArticlesViewState)
        if (value is NewsArticlesViewState.ShowNewsArticles) {
            assertEquals(value.newsArticles[0], "Favourites")
            assertEquals(value.newsArticles[1], listOfArticles[0])
        } else fail()
    }


    @Test
    fun `when list is refreshed - articles list with current week article - should show article with ThisWeek header`
            () {

        val currWeekArticle = Article(id = "002",
                thumbnail = "",
                sectionId = "",
                published = Date(),
                favourite = false,
                url = "url",
                title = "Test Title",
                sectionName = "",
                body = "")

        whenever(newsArticleRepo.requestData(any())).thenAnswer {
            val callback = it.getArgument<(NewsArticlesState) -> Unit>(0)
            callback.invoke(NewsArticlesState.Success(listOf(currWeekArticle)))
        }

        runBlocking {
            viewModel.onListRefreshed()
        }

        val value = LiveDataTestUtil.getValue(viewModel.newsArticlesViewState)
        if (value is NewsArticlesViewState.ShowNewsArticles) {
            assertEquals(value.newsArticles[0], "This week")
            assertEquals(value.newsArticles[1], currWeekArticle)
        } else fail()
    }

    @Test
    fun `when list is refreshed - articles list with last week article- should show article with LastWeek header`() {

        val lastWeekArticle = Article(id = "002",
                thumbnail = "",
                sectionId = "",
                published = get8DaysAgoDate(),
                favourite = false,
                url = "url",
                title = "Test Title",
                sectionName = "",
                body = "")

        whenever(newsArticleRepo.requestData(any())).thenAnswer {
            val callback = it.getArgument<(NewsArticlesState) -> Unit>(0)
            callback.invoke(NewsArticlesState.Success(listOf(lastWeekArticle)))
        }

        runBlocking {
            viewModel.onListRefreshed()
        }

        val value = LiveDataTestUtil.getValue(viewModel.newsArticlesViewState)
        if (value is NewsArticlesViewState.ShowNewsArticles) {
            assertEquals(value.newsArticles[0], "Last week")
            assertEquals(value.newsArticles[1], lastWeekArticle)
        } else fail()
    }

    private fun get8DaysAgoDate(): Date {
        val date = Calendar.getInstance()
        date.add(Calendar.DATE, -8)
        return date.time
    }
}