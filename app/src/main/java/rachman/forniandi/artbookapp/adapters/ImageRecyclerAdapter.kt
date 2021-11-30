package rachman.forniandi.artbookapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import rachman.forniandi.artbookapp.databinding.ItemArtBinding
import rachman.forniandi.artbookapp.databinding.ItemImageBinding
import rachman.forniandi.artbookapp.room.Art
import javax.inject.Inject

class ImageRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<ImageRecyclerAdapter.ImageViewHolder>(){

    private var onItemClickListener:((String) -> Unit)? = null

    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var images : List<String>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding= ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImageViewHolder(binding)
    }

    fun setOnItemClickListener(listener:(String) ->Unit){
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val urlImg = images[position]
        holder.itemView.apply{
            glide.load(urlImg).into(holder.imgArt)
            setOnClickListener {
                onItemClickListener?.let {
                    it(urlImg)
                }
            }
        }
    }

    class ImageViewHolder (val view:ItemImageBinding) : RecyclerView.ViewHolder(view.root){
        val imgArt = view.imageItem
        /*fun bind(urlImg: String) {
            glide.load(urlImg).into(imgArt)
        }*/


    }

    override fun getItemCount(): Int {
        return images.size
    }
}