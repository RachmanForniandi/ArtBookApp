package rachman.forniandi.artbookapp.repositories

import androidx.lifecycle.LiveData
import rachman.forniandi.artbookapp.models.ImageResponse
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.utils.Resource

interface ArtRepositoryInterface {

    suspend fun insertDataArt(art:Art)

    suspend fun deleteArt(art: Art)

    fun getDataArt():LiveData<List<Art>>

    suspend fun searchImage(imgString:String):Resource<ImageResponse>
}