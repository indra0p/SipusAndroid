package com.sipus.core.data.remote

import com.sipus.core.data.datastore.SessionManager
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val url = original.url.toString()

        // Skip auth for login and register endpoints
        if (url.contains("masuk.php") || url.contains("daftar.php")) {
            return chain.proceed(original)
        }

        val token = runBlocking { sessionManager.token.firstOrNull() }
        val request = if (!token.isNullOrEmpty()) {
            original.newBuilder()
                .header("Authorization", "Bearer $token")
                .header("Content-Type", "application/json")
                .build()
        } else {
            original
        }
        return chain.proceed(request)
    }
}
