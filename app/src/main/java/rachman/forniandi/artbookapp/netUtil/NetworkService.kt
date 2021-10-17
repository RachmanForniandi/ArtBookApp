package rachman.forniandi.artbookapp.netUtil

import rachman.forniandi.artbookapp.models.ImageResponse
import rachman.forniandi.artbookapp.netUtil.NetworkConfig.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/api/")
    suspend fun imageDataSearch(
        @Query("q")searchQuery:String,
        @Query("key")apiKey:String = API_KEY
    ):Response<ImageResponse>
}