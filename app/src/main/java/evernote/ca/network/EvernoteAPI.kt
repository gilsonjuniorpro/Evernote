package evernote.ca.network

import evernote.ca.model.Note
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EvernoteAPI {

    @GET("/")
    fun listNotes(): Call<List<Note>>

    @GET("/{id}")
    fun getNote(@Path("id") id: Int): Call<Note>

    @POST("/create")
    fun createNote(@Body note: Note): Call<Note>

}