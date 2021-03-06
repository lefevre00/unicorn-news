package fr.gerdev.unicornNews.model

import android.arch.lifecycle.LiveData
import android.content.Context
import fr.gerdev.unicornNews.repository.ArticleRepository
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class SearchLiveData(private val query: String, private val context: Context) : LiveData<List<Article>>() {

    private lateinit var articleRepository: ArticleRepository
    private lateinit var executor: ExecutorService

    override fun onInactive() {
        executor.shutdownNow()
        value = emptyList()
    }

    override fun onActive() {
        articleRepository = ArticleRepository(context)
        executor = Executors.newSingleThreadExecutor()
        executor.execute(Loader())
    }

    inner class Loader : Runnable {
        override fun run() {
            try {
                postValue(articleRepository.searchArticles(query))
            } catch (e: InterruptedException) {
                Timber.e("Loader with query $query interrupted")
            }
        }
    }
}