package android.pos1

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("api/accounts/login_api")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("api/accounts/logout_api")
    fun logout(): Call<Void>

    @GET("api/sales/api_getitems")
    fun getItems(): Call<ItemResponse>


}
