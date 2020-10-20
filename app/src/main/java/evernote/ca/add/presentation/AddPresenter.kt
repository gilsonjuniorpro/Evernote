package evernote.ca.add.presentation

import evernote.ca.model.Note
import evernote.ca.model.RemoteDataSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class AddPresenter(
        private val view: Add.View,
        private val dataSource: RemoteDataSource
) : Add.Presenter {

    private val compositeDisposable = CompositeDisposable()

    private val getNoteObserver : DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onNext(note: Note) {
                view.displayNote(note.title ?: "", note.body ?: "")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("loading error")
            }

            override fun onComplete() {
                println("complete")
            }
        }

    private val createNoteObserver : DisposableObserver<Note>
        get() = object : DisposableObserver<Note>() {
            override fun onNext(note: Note) {
                view.returnToHome()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                view.displayError("loading error")
            }

            override fun onComplete() {
                println("complete")
            }
        }

    override fun createNote(title: String, body: String) {
        if(title.isEmpty() || body.isEmpty()){
            view.displayError("Please type the title and the body of the note")
            return
        }
        val note = Note(title = title, body = body)

        val disposable = dataSource.createNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(createNoteObserver)

        compositeDisposable.add(disposable)
    }

    override fun getNote(noteId: Int) {
        val disposable = dataSource.getNote(noteId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getNoteObserver)

        compositeDisposable.add(disposable)
    }

    override fun stop() {
        compositeDisposable.clear()
    }
}