package evernote.ca.model

import javax.inject.Inject

class User @Inject constructor(private val note: Note) {


    fun showNoteTitle(){
        println("title: ${note.title}")
    }
}