package evernote.ca

import evernote.ca.home.presentation.Home
import evernote.ca.home.presentation.HomePresenter
import evernote.ca.model.Note
import evernote.ca.model.RemoteDataSource
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomePresenterTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxSchedulerRule()

    @Mock
    private lateinit var mockView: Home.View

    @Mock
    private lateinit var mockDataSource: RemoteDataSource

    lateinit var homePresenter: HomePresenter

    private val fakeAllNotes: List<Note>
        get() = arrayListOf(
            Note(1, "NoteA", "NoteA Description", "01/10/2012", "Welcome NoteA"),
            Note(2, "NoteB", "NoteB Description", "02/10/2012", "Welcome NoteB"),
            Note(3, "NoteC", "NoteC Description", "03/10/2012", "Welcome NoteC")
        )

    @Before
    fun setup(){
        homePresenter = HomePresenter(mockView, mockDataSource)
    }

    @Test
    fun `test must get all notes`() {
        // given
        Mockito.doReturn(Observable.just(fakeAllNotes)).`when`(mockDataSource).listNotes()

        // when
        homePresenter.getAllNotes()

        // then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayNotes(fakeAllNotes)
    }

    @Test
    fun `test must show empty notes`() {
        // given
        Mockito.doReturn(Observable.just(arrayListOf<Note>())).`when`(mockDataSource).listNotes()

        // when
        homePresenter.getAllNotes()

        // then
        Mockito.verify(mockDataSource).listNotes()
        Mockito.verify(mockView).displayEmptyNotes()
    }
}