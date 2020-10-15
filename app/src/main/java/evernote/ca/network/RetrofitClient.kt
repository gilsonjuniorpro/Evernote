package evernote.ca.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val EVERNOTE_BASE_URL = "https://myevernote.glitch.me/"

    val evernoteApi = Retrofit.Builder()
        .baseUrl(EVERNOTE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(EvernoteAPI::class.java)

}
