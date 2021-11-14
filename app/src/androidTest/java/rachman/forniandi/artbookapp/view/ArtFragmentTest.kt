package rachman.forniandi.artbookapp.view

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.launchFragmentInHiltContainer
import rachman.forniandi.artbookapp.views.fragments.ArtFragment
import rachman.forniandi.artbookapp.views.fragments.ArtFragmentDirections
import rachman.forniandi.artbookapp.views.fragments.ArtFragmentFactory
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){

        hiltRule.inject()

    }

    @Test
    fun testNavigationFromArtToArtDetails(){

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.fab_add)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
        )
    }

}