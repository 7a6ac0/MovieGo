package tabacowang.me.moviego.di

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tabacowang.me.moviego.BuildConfig
import tabacowang.me.moviego.data.remote.HeaderInterceptor
import tabacowang.me.moviego.data.remote.LanguageInterceptor
import java.util.concurrent.TimeUnit

val networkModule = module {
    // Provide HttpLoggingInterceptor
    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
        }
    }

    single { HeaderInterceptor() }

    single { LanguageInterceptor() }

    // Provide OkHttpClient
    single {
        val defaultTimeout = 10L

        OkHttpClient.Builder()
            .connectTimeout(defaultTimeout, TimeUnit.SECONDS)
            .readTimeout(defaultTimeout, TimeUnit.SECONDS)
            .writeTimeout(defaultTimeout, TimeUnit.SECONDS)
            .addInterceptor(get<HeaderInterceptor>())
            .addInterceptor(get<LanguageInterceptor>())
            .addInterceptor(get<HttpLoggingInterceptor>())
            .protocols(listOf(Protocol.HTTP_1_1, Protocol.HTTP_2))
            .build()
    }

    single<Converter.Factory> { GsonConverterFactory.create(get()) }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(BuildConfig.API_ROOT)
            .addConverterFactory(get())
            .build()
    }
}