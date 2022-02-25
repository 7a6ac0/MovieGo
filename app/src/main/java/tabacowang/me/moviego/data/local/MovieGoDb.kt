package tabacowang.me.moviego.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GenreModel::class], version = 1)
abstract class MovieGoDb : RoomDatabase() {
    abstract fun movieGoDao(): MovieGoDao
}