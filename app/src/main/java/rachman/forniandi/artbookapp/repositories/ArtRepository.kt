package rachman.forniandi.artbookapp.repositories

import androidx.lifecycle.LiveData
import rachman.forniandi.artbookapp.models.ImageResponse
import rachman.forniandi.artbookapp.netUtil.NetworkService
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.room.ArtDao
import rachman.forniandi.artbookapp.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val networkService: NetworkService
):ArtRepositoryInterface {
    override suspend fun insertDataArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getDataArt(): LiveData<List<Art>> {
       return artDao.observeArts()
    }

    override suspend fun searchImage(imgString: String): Resource<ImageResponse> {
        return try {
            val response = networkService.imageDataSearch(imgString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                }?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e:Exception){
            Resource.error("No Data Available.",null)
        }
    }
}