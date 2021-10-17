package rachman.forniandi.artbookapp.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import rachman.forniandi.artbookapp.R
import rachman.forniandi.artbookapp.adapters.ListArtAdapter
import rachman.forniandi.artbookapp.databinding.FragmentArtBinding
import rachman.forniandi.artbookapp.room.Art
import rachman.forniandi.artbookapp.viewmodels.ArtViewModel
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ArtFragment @Inject constructor(val listArtAdapter: ListArtAdapter): Fragment(R.layout.fragment_art) {

    private var fragmentBinding: FragmentArtBinding?= null
    private lateinit var viewModel:ArtViewModel
    /*private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedItem = listArtAdapter.arts[layoutPosition]
            viewModel.deleteArt(selectedItem)
        }

    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)
        val binding = FragmentArtBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.rvDataArt.adapter = listArtAdapter
        //binding.rvDataArt.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDataArt.let { swipeToDelete(it) }



        binding.fabAdd.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun swipeToDelete(recyclerview: RecyclerView) {
        val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val layoutPosition = viewHolder.adapterPosition
                val selectedItem = listArtAdapter.arts[layoutPosition]
                viewModel.deleteArt(selectedItem)

                undoDeleteData(viewHolder.itemView,selectedItem,layoutPosition)
            }
        }
        val itemTouchHelper =ItemTouchHelper(swipeCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerview)
    }

    private fun undoDeleteData(view: View, deletedItem: Art, position: Int) {
        val snackBar= Snackbar.make(view,"Delete '${deletedItem.name}'", Snackbar.LENGTH_LONG)
            snackBar.setAction("Undo"){
                    viewModel.insertArt(deletedItem)
                    listArtAdapter.notifyItemChanged(position)
                }
        snackBar.show()

    }

    private fun subscribeToObservers(){
        viewModel.artListData.observe(viewLifecycleOwner, Observer {
            listArtAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        fragmentBinding= null
        super.onDestroyView()
    }


}