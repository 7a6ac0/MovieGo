package tabacowang.me.moviego.data.repo

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import tabacowang.me.moviego.util.Resource

abstract class BaseRepo {
    suspend fun <T> coroutineApiCall(
        coroutineDispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T?
    ): T? {
        return withContext(coroutineDispatcher) {
            try {
                apiCall.invoke()
            } catch (e: Exception) {
                null
            }
        }
    }

    fun <T> flowApiCall(
        coroutineDispatcher: CoroutineDispatcher,
        apiCall: suspend () -> T
    ): Flow<T> {
        return flow {
            emit(apiCall.invoke())
        }
        .catch { e -> println(e.toString()) }
        .flowOn(coroutineDispatcher)
    }
}