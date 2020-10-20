package evernote.ca

import com.nhaarman.mockito_kotlin.anyOrNull
import evernote.ca.add.presentation.Add
import evernote.ca.add.presentation.AddPresenter
import evernote.ca.home.presentation.HomePresenter
import evernote.ca.model.Note
import evernote.ca.model.RemoteDataSource
import io.reactivex.Observable
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AddPresenterTest : BaseTest() {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Add.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    @Captor
    private lateinit var noteArgCaptor: ArgumentCaptor<Note>

    lateinit var addPresenter: AddPresenter

    @Before
    fun setup(){
        addPresenter = AddPresenter(mockView, mockDataSource)
    }

    @Test
    fun `test must not add note with empty body`() {
        // when
        addPresenter.createNote("", "")

        // then
        Mockito.verify(mockView).displayError("Please type the title and the body of the note")
    }

    @Test
    fun `test must add note`() {
        // given
        val note = Note(title = "NoteA", body = "Description NoteA")
        Mockito.doReturn(Observable.just(note)).`when`(mockDataSource).createNote(captureArg(
            noteArgCaptor
        ))

        // when
        addPresenter.createNote("NoteA", "Description NoteA")

        // then
        Mockito.verify(mockDataSource).createNote(captureArg(noteArgCaptor))

        Assert.assertThat(noteArgCaptor.value.title, CoreMatchers.equalTo("NoteA"))
        Assert.assertThat(noteArgCaptor.value.body, CoreMatchers.equalTo("Description NoteA"))

        Mockito.verify(mockView).returnToHome()
    }

    @Test
    fun `test must show error when create fails`() {
        // Given
        Mockito.doReturn(Observable.error<Throwable>(Throwable("server error")))
            .`when`(mockDataSource).createNote(anyOrNull())

        // when
        addPresenter.createNote("NoteA", "Description NoteA")

        // then
        Mockito.verify(mockDataSource).createNote(captureArg(noteArgCaptor))

        Assert.assertThat(noteArgCaptor.value.title, CoreMatchers.equalTo("NoteA"))
        Assert.assertThat(noteArgCaptor.value.body, CoreMatchers.equalTo("Description NoteA"))

        Mockito.verify(mockView).displayError("Error creating note")
    }
}