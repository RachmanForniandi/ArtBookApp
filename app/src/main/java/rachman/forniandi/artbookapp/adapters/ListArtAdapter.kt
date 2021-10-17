package rachman.forniandi.artbookapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import rachman.forniandi.artbookapp.databinding.ItemArtBinding
import rachman.forniandi.artbookapp.room.Art
import javax.inject.Inject

class ListArtAdapter @Inject constructor(
    private val glide: RequestManager
): RecyclerView.Adapter<ListArtAdapter.ListArtHolder>() {



    private val diffUtil = object : DiffUtil.ItemCallback<Art>() {
        override fun areItemsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Art, newItem: Art): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var arts:List<Art>
    get() = recyclerListDiffer.currentList
    set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListArtHolder {
        val binding= ItemArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ListArtHolder(binding)
    }


    override fun onBindViewHolder(holder: ListArtHolder, position: Int) {
        val items = recyclerListDiffer.currentList[position]
        holder.bind(items)
        /*holder.itemView.apply {

        }*/
    }

    override fun getItemCount(): Int {
        return arts.size
    }

    inner class ListArtHolder(val view:ItemArtBinding):RecyclerView.ViewHolder(view.root) {
        fun bind(items: Art) {
            view.tvArtName.text = items.name
            view.tvArtistName.text = items.artistName
            view.tvYear.text = items.year.toString()
            glide.load(items.imageUrl).into(view.imgPic)
        }

    }
}