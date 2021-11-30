package rachman.forniandi.artbookapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rachman.forniandi.artbookapp.models.ImageResponse
import rachman.forniandi.artbookapp.repositories.ArtRepositoryInterface
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.utils.Resource

class FakeArtRepositoryAndroid : ArtRepositoryInterface {
    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun insertDataArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getDataArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun searchImage(imgString: String): Resource<ImageResponse> {
        return  Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}