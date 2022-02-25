package tabacowang.me.moviego.di

import androidx.room.Room
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import tabacowang.me.moviego.data.local.MovieGoDb
import tabacowang.me.moviego.data.remote.MovieApiService
import tabacowang.me.moviego.data.repo.MovieApiRepo
import tabacowang.me.moviego.data.repo.MovieApiApiRepoImpl
import tabacowang.me.moviego.data.repo.MovieDbRepo
import tabacowang.me.moviego.data.repo.MovieDbRepoImpl
import tabacowang.me.moviego.util.CalendarDeserializer
import java.util.*

val apiModule = module {
    single { get<Retrofit>().create(MovieApiService::class.java) }
}

val dbModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            MovieGoDb::class.java,
            MovieGoDb::class.java.simpleName
        ).fallbackToDestructiveMigration().build()
    }

    single { get<MovieGoDb>().movieGoDao() }
}

val gsonModule = module {
    single { CalendarDeserializer() }
    single {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Calendar::class.java, get<CalendarDeserializer>())
        gsonBuilder.create()
    }
}

val repoModule = module {
    single<MovieApiRepo> { MovieApiApiRepoImpl() }
    single<MovieDbRepo> { MovieDbRepoImpl() }
}