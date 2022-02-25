package tabacowang.me.moviego.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class GenreModel(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?
)