package rachman.forniandi.artbookapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.adapters.ImageRecyclerAdapter
import rachman.forniandi.artbookapp.databinding.FragmentArtBinding
import rachman.forniandi.artbookapp.databinding.FragmentImageGalleryBinding
import rachman.forniandi.artbookapp.databinding.ItemImageBinding
import rachman.forniandi.artbookapp.utils.Status
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel
import javax.inject.Inject

@AndroidEntryPoint
class ImageGalleryFragment
@Inject constructor(
    val imageRecyclerAdapter: ImageRecyclerAdapter
) : Fragment(R.layout.fragment_image_gallery) {
    lateinit var viewModel: ArtViewModel
    private var imgGalleryBinding: FragmentImageGalleryBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentImageGalleryBinding.bind(view)
        imgGalleryBinding = binding


        var job: Job? = null
        imgGalleryBinding?.etSearch?.addTextChangedListener { 
            job?.cancel()
            job = lifecycleScope.launch { 
                delay(1000)
                it.let { 
                    if (it.toString().isNotEmpty()){
                        viewModel.searchForImage(it.toString())
                    }
                }
            }
        }
        
        subscribeToObserversByViewModel()

        imgGalleryBinding?.listArtData?.adapter = imageRecyclerAdapter
        //imgGalleryBinding?.listArtData?.layoutManager = GridLayoutManager(requireContext(),3)

        imageRecyclerAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }
    }

    private fun subscribeToObserversByViewModel() {
        viewModel.imgList.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->  imageResult?.previewURL }
                    imageRecyclerAdapter.images = (urls ?: listOf()) as List<String>
                    imgGalleryBinding?.progressBar?.visibility = View.GONE

                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(),it.message ?: "Error", Toast.LENGTH_LONG).show()
                    imgGalleryBinding?.progressBar?.visibility = View.GONE

                }

                Status.LOADING -> {
                    imgGalleryBinding?.progressBar?.visibility = View.VISIBLE

                }
            }
        })
    }

    override fun onDestroyView() {
        imgGalleryBinding =null
        super.onDestroyView()
    }

}