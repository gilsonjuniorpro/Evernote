package evernote.ca.home.presentation

import evernote.ca.model.Note

interface Home {
    interface Presenter {
        fun getAllNotes()
        fun stop()
    }

    interface View {
        fun displayNotes(notes: List<Note>)
        fun displayEmptyNotes()
        fun displayError(message: String)
    }
}