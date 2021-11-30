package rachman.forniandi.artbookapp.views.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import rachman.forniandi.artbookapp.adapters.ImageRecyclerAdapter
import rachman.forniandi.artbookapp.adapters.ListArtAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val imageRecyclerAdapter: ImageRecyclerAdapter,
    private val glide:RequestManager,
private val listArtAdapter: ListArtAdapter):FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name ->ArtFragment(listArtAdapter)
            ArtDetailsFragment::class.java.name ->ArtDetailsFragment(glide)
            ImageGalleryFragment::class.java.name -> ImageGalleryFragment(imageRecyclerAdapter)
            else->super.instantiate(classLoader, className)
        }

    }
}