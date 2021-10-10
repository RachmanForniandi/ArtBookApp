package rachman.forniandi.artbookapp.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import rachman.forniandi.artbookapp.models.ImageResponse
import rachman.forniandi.artbookapp.repositories.ArtRepositoryInterface
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.utils.Resource
import java.lang.Exception

class ArtViewModel @ViewModelInject constructor(
    private val repoInterface: ArtRepositoryInterface):ViewModel() {
        //For Art Fragment

        val artListData = repoInterface.getDataArt()

    //Image API Fragment

    private val images = MutableLiveData<Resource<ImageResponse>>()
    val imgList: LiveData<Resource<ImageResponse>>
    get() = images

    private val selectedImg = MutableLiveData<String>()
    val selectedImageUrl: LiveData<String>
    get() = selectedImg

    //For Art Details Fragment
    private var insertArtMsg = MutableLiveData<Resource<Art>>()
    val insertMsg: LiveData<Resource<Art>>
    get() = insertArtMsg

    fun resetInsertArtMsg(){
        insertArtMsg = MutableLiveData<Resource<Art>>()
    }

    fun setSelectedImage(url:String){
        selectedImg.postValue(url)
    }

    fun deleteArt(art: Art) = viewModelScope.launch {
        repoInterface.deleteArt(art)
    }

    fun insertArt(art: Art) = viewModelScope.launch {
        repoInterface.insertDataArt(art)
    }

    fun makeArt(name:String,artistName:String,year:String){
        if (name.isEmpty() || artistName.isEmpty() || year.isEmpty()){
            insertArtMsg.postValue(Resource.error("Please input the data correctly",null))
            return
        }

        val yearInt = try {
            year.toInt()
        }catch (e:Exception){
            insertArtMsg.postValue(Resource.error("Please input the data correctly",null))
            return
        }

        val art = Art(name,artistName,yearInt,selectedImg.value ?:"")
        insertArt(art)
        setSelectedImage("")
        insertArtMsg.postValue(Resource.success(art))
    }
    fun searchForImage(searchImg:String){
        if (searchImg.isEmpty()) {
            return
        }

        images.value = Resource.loading(null)
        viewModelScope.launch {
            val response = repoInterface.searchImage(searchImg)
            images.value = response
        }
    }



}