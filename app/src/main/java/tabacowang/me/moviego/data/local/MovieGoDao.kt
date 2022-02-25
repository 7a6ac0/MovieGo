package tabacowang.me.moviego.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieGoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenre(genre: GenreModel)

    @Query("SELECT * FROM genre")
    suspend fun getGenreList(): List<GenreModel>?
}