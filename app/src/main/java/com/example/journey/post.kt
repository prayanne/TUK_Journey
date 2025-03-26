package com.example.journey

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val username: String, val userpassword: String)
data class LoginResponse(val result: String, val message: String)
data class ResisterRequest(val username: String, val useremail: String, val userpassword: String)
data class ResisterResponse(val result: String, val message: String)

interface ApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/register")
    suspend fun register(@Body request: ResisterRequest): Response<ResisterResponse>
/*sd
    @GET("/user/info")
    suspend fun getUserInfo(): Response<UserInfo>
*/
}


object RetrofitClient {
    private const val BASE_URL = "https://api.prayanne.co.kr"
    private val client = OkHttpClient.Builder().build()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환
            .build()
            .create(ApiService::class.java)
    }
}
