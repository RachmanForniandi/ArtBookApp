package rachman.forniandi.artbookapp.dbRoom

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import dagger.Provides
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import rachman.forniandi.artbookapp.getOrAwaitValue
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.room.ArtDao
import rachman.forniandi.artbookapp.room.ArtDatabase
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var db:ArtDatabase


    private lateinit var dao:ArtDao

    @Before
    fun setup(){

        /*db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),ArtDatabase::class.java
        ).allowMainThreadQueries().build()*/

        hiltRule.inject()

        dao = db.artDao()
    }

    @After
    fun tearDown(){
        db.close()
    }


    @Test
    fun insertDataArtTesting() = runBlockingTest {
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)
        dao.insertArt(exampleArt)

        val list= dao.observeArts().getOrAwaitValue()
        assertThat(list).contains(exampleArt)

    }

    @Test
    fun deleteDataArtTesting()= runBlockingTest {
        val exampleArt = Art("Mona Lisa","Da Vinci",1700,"test.com",1)

        dao.insertArt(exampleArt)
        dao.deleteArt(exampleArt)

        val list= dao.observeArts().getOrAwaitValue()
        assertThat(list).doesNotContain(exampleArt)
    }



}