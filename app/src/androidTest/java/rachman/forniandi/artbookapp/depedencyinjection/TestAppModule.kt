package rachman.forniandi.artbookapp.depedencyinjection

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import rachman.forniandi.artbookapp.room.ArtDatabase
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context: Context)=
        Room.inMemoryDatabaseBuilder(
            context, ArtDatabase::class.java
        ).allowMainThreadQueries().build()
}