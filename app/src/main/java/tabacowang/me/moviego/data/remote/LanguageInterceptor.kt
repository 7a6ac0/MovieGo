package tabacowang.me.moviego.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class LanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val locale = Locale.getDefault()
        val url = request.url.newBuilder().addQueryParameter("language", "${locale.language}-${locale.country}").build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}