package rachman.forniandi.artbookapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rachman.forniandi.artbookapp.MainCoroutineRule
import rachman.forniandi.artbookapp.getOrAwaitValueTest
import rachman.forniandi.artbookapp.repo.FakeArtRepository
import rachman.forniandi.artbookapp.utils.Status
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    private lateinit var viewModel: ArtViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup(){
        viewModel = ArtViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa","Da Vinci","")
        val value = viewModel.insertMsg.getOrAwaitValueTest()
        //utk hasil true
        assertThat(value.status).isEqualTo(Status.ERROR)
        //untuk hasil false
        //assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("","Da Vinci","1500")
        val value = viewModel.insertMsg.getOrAwaitValueTest()
        //untuk hasil true
        assertThat(value.status).isEqualTo(Status.ERROR)
        //untuk hasil false
        //assertThat(value.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona Lisa","","1500")
        val value = viewModel.insertMsg.getOrAwaitValueTest()
        //untuk hasil true
        assertThat(value.status).isEqualTo(Status.ERROR)
        //untuk hasil false
        //assertThat(value.status).isEqualTo(Status.SUCCESS)
    }
}