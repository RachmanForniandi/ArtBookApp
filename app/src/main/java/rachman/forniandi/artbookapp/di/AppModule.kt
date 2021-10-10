package rachman.forniandi.artbookapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import rachman.forniandi.artbookapp.netUtil.NetworkConfig
import rachman.forniandi.artbookapp.netUtil.NetworkConfig.BASE_URL
import rachman.forniandi.artbookapp.netUtil.NetworkService
import rachman.forniandi.artbookapp.room.ArtDatabase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtBookDb"
        ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase)= database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitNetworkData():NetworkService{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(NetworkService::class.java)
    }
}