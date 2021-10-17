package rachman.forniandi.artbookapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.databinding.FragmentArtDetailsBinding
import rachman.forniandi.artbookapp.utils.Status
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
class ArtDetailsFragment @Inject constructor(val glide: RequestManager): Fragment(R.layout.fragment_art_details) {

    private lateinit var viewModel: ArtViewModel

    private var binding:FragmentArtDetailsBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        val binding = FragmentArtDetailsBinding.bind(view)

        subscribeToObservers()

        binding.imgArt.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageGalleryFragment())
        }

        val callback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)

        binding.saveButton.setOnClickListener {
            viewModel.makeArt(binding.etNameArt.text.toString(),
            binding.etNameArtist.text.toString(),
            binding.etYear.text.toString())
        }

    }

    private fun subscribeToObservers(){
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->
            binding?.let {
                glide.load(url).into(it.imgArt)
            }
        })

        viewModel.insertMsg.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.SUCCESS->{
                    Toast.makeText(requireContext(),"Success",Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }

                Status.ERROR->{
                    Toast.makeText(requireContext(),it.message ?: "Error",Toast.LENGTH_LONG).show()
                }

                Status.LOADING->{

                }
            }
        })
    }



    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}