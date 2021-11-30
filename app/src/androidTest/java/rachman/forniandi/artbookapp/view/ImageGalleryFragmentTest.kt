package rachman.forniandi.artbookapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import rachman.forniandi.artbookapp.R
import com.google.common.truth.Truth.assertThat
import rachman.forniandi.artbookapp.adapters.ImageRecyclerAdapter
import rachman.forniandi.artbookapp.getOrAwaitValue
import rachman.forniandi.artbookapp.launchFragmentInHiltContainer
import rachman.forniandi.artbookapp.repo.FakeArtRepositoryAndroid
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel
import rachman.forniandi.artbookapp.views.fragments.ArtDetailsFragment
import rachman.forniandi.artbookapp.views.fragments.ArtFragmentFactory
import rachman.forniandi.artbookapp.views.fragments.ImageGalleryFragment
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageGalleryFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){

        hiltRule.inject()

    }

    @Test
    fun testSelectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "atilsamancioglu.com"
        val testViewModel = ArtViewModel(FakeArtRepositoryAndroid())

        launchFragmentInHiltContainer<ImageGalleryFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
            viewModel = testViewModel

        }
        Espresso.onView(ViewMatchers.withId(R.id.list_art_data)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(
                0, click()
            )
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)
    }
}