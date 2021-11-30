package rachman.forniandi.artbookapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.getOrAwaitValue
import rachman.forniandi.artbookapp.launchFragmentInHiltContainer
import rachman.forniandi.artbookapp.repo.FakeArtRepositoryAndroid
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel
import rachman.forniandi.artbookapp.views.fragments.ArtDetailsFragment
import rachman.forniandi.artbookapp.views.fragments.ArtDetailsFragmentDirections
import rachman.forniandi.artbookapp.views.fragments.ArtFragment
import rachman.forniandi.artbookapp.views.fragments.ArtFragmentFactory
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {
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
    fun testNavigationFromArtDetailsToImageGalleryFragment(){

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }
        Espresso.onView(ViewMatchers.withId(R.id.imgArt)).perform(click())

        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageGalleryFragment()
        )
    }


    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        //pressBack()
        Espresso.pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun testSave() {
        val testViewModel = ArtViewModel(FakeArtRepositoryAndroid())

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.etNameArt)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.etNameArtist)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.etYear)).perform(replaceText("1700"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artListData.getOrAwaitValue()).contains(
            Art(
                "Mona Lisa",
                "Da Vinci",
                1700,"")
        )
    }
}