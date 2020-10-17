package evernote.ca.model

class User(private val name: String) {

    val note: Note

    init {
        note = Note(1, "First Note")
    }

    fun showNoteTitle(){
        println("Note of $name | title: ${note.title}")
    }
}