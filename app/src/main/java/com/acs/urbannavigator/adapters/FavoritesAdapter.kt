package com.acs.urbannavigator.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.R
import com.acs.urbannavigator.models.FavoriteItem
import com.acs.urbannavigator.models.museumDetailsModel.Exhibit
import com.squareup.picasso.Picasso

class FavoritesAdapter(private val mList: List<FavoriteItem>):RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {

    var onItemClick : ((FavoriteItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.museums_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentFavorite = mList[position]
        holder.titleTextView.text = currentFavorite.title

        val imagePath = "https://media.izi.travel/" + currentFavorite.contentProviderUuid + "/" +currentFavorite.imageUuid + "_480x360.jpg"
        Picasso.get().load(imagePath).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentFavorite)
        }
        holder.starTextView.isInvisible = true
        holder.starImageView.isInvisible = true
        holder.heartImageView.setColorFilter(ContextCompat.getColor(holder.itemView.context, R.color.red))
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val titleTextView: TextView
        val imageView: ImageView
        val starTextView: TextView
        val starImageView: ImageView
        val heartImageView: ImageView

        init {
            titleTextView = ItemView.findViewById(R.id.museum_title_textView)
            imageView = ItemView.findViewById(R.id.museum_row_imageView)
            starTextView = ItemView.findViewById(R.id.star_textView)
            starImageView = ItemView.findViewById(R.id.star_imageView)
            heartImageView = ItemView.findViewById(R.id.heart_imageView)
        }
    }
}