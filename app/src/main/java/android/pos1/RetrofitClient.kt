package android.pos1

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "http://192.168.43.201:8000/pos1/index.php/"

    // Logging Interceptor
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Cookie Jar for Handling Cookies
    private val cookieJar = object : CookieJar {
        private val cookieStore: MutableMap<String, MutableList<Cookie>> = mutableMapOf()

        override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
            println("Saving cookies for ${url.host}: $cookies")
            cookieStore[url.host]?.addAll(cookies) ?: run {
                cookieStore[url.host] = cookies.toMutableList()
            }
        }

        override fun loadForRequest(url: HttpUrl): List<Cookie> {
            val cookies = cookieStore[url.host] ?: emptyList()
            println("Loading cookies for ${url.host}: $cookies")
            return cookies
        }
    }

    // OkHttpClient with Cookie Handling and Logging
    private val httpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .addInterceptor(logging) // Add logging
        .addInterceptor { chain ->
            val request = chain.request()
            println("Request URL: ${request.url}")
            println("Headers: ${request.headers}")
            chain.proceed(request)
        }
        .build()

    // Retrofit Instance
    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
