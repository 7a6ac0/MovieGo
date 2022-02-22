package tabacowang.me.moviego.di

import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import tabacowang.me.moviego.data.remote.MovieApiService
import tabacowang.me.moviego.data.repo.MovieRepo
import tabacowang.me.moviego.data.repo.MovieRepoImpl
import tabacowang.me.moviego.util.CalendarDeserializer
import java.util.*

val apiModule = module {
    single { get<Retrofit>().create(MovieApiService::class.java) }
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
    single<MovieRepo> { MovieRepoImpl() }
}