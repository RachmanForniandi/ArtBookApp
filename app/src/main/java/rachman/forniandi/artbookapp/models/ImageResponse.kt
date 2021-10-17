package rachman.forniandi.artbookapp.models

import com.google.gson.annotations.SerializedName

data class ImageResponse(

	@SerializedName("hits")
	val hits: List<ImageResult?>? = null,

	@SerializedName("total")
	val total: Int? = null,

	@SerializedName("totalHits")
	val totalHits: Int? = null
)
