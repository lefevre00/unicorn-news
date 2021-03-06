package fr.gerdev.unicornNews.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import fr.gerdev.unicornNews.model.Article

// ger 03/03/18
@Dao
interface ArticleDao {
    @get:Query("SELECT * FROM article")
    val all: List<Article>

    @Query("SELECT * FROM article WHERE id IN (:arg0)")
    fun loadAllByIds(articleIds: IntArray): List<Article>

    @Query("SELECT * FROM article WHERE source IN (:arg0) ORDER BY downloadDate desc")
    fun getBySources(sourceNames: List<String>): List<Article>

    @Query("SELECT * FROM article WHERE title like (:arg0) AND " +
            "title like (:arg1) AND title like (:arg2) AND title like (:arg3) AND " +
            "description like (:arg0) AND description like (:arg1) AND " +
            "description like (:arg2) AND description like (:arg3) ORDER BY downloadDate desc")
    fun search(first: String = "%%", second: String = "%%", third: String = "%%", fourth: String = "%%"): List<Article>

    @Query("SELECT COUNT(id)=0 FROM article WHERE title  = (:arg0)")
    fun titleNotExist(title: String?): Boolean

    @Query("SELECT COUNT(id)=0 FROM article WHERE description  = (:arg0)")
    fun descriptionNotExist(description: String?): Boolean

    @Query("SELECT COUNT(id)=0 FROM article WHERE link  = (:arg0)")
    fun linkNotExist(link: String?): Boolean

    @Query("UPDATE article SET read = 1 WHERE link  = (:arg0)")
    fun setReaded(link: String)

    @Insert
    fun insertAll(vararg article: Article)

    @Delete
    fun delete(article: Article)
}
