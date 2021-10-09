package rachman.forniandi.artbookapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.databinding.FragmentArtBinding


class ArtFragment : Fragment(R.layout.fragment_art) {

    private var fragmentBinding: FragmentArtBinding?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtBinding.bind(view)
        fragmentBinding = binding

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    override fun onDestroyView() {
        fragmentBinding= null
        super.onDestroyView()
    }


}