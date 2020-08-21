package com.yogesh.imgurdemo.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yogesh.imgurdemo.R
import com.yogesh.imgurdemo.model.Image


class ImageRecyclerViewAdapter(_context: Context, _itemClickListener: RecyclerItemClickListener) :
    RecyclerView.Adapter<ImageRecyclerViewAdapter.ImageViewHolder>() {

    private val context = _context
    private val itemClickListener = _itemClickListener
    private var imageList = ArrayList<Image>()

    fun setUserList(_userList: List<Image>) {
        imageList = _userList as ArrayList<Image>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.image_item_layout, parent, false)
        return ImageViewHolder(
            view
        )
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (position < imageList.size) {
            val image = imageList[position]
            Glide.with(holder.galleryImageView.context)
                .setDefaultRequestOptions(RequestOptions().centerInside())
                .load(Uri.parse(image.link))
                .into(holder.galleryImageView)
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClickListener(position, imageList[position].id)

        }
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val galleryImageView: ImageView = itemView.findViewById(R.id.imageView)
    }

}