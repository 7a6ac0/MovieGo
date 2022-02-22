package tabacowang.me.moviego.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import tabacowang.me.moviego.BuildConfig

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()

        builder.addHeader("Content-Type", "application/json;charset=utf-8")
        builder.addHeader("Authorization", "Bearer ${BuildConfig.TMDB_API_TOKEN}")
        return chain.proceed(builder.build())
    }
}