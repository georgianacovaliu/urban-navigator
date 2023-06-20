package com.acs.urbannavigator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.R
import com.acs.urbannavigator.models.museumModel.MuseumItem
import com.squareup.picasso.Picasso

class MuseumsAdapter(private val mList: List<MuseumItem>):RecyclerView.Adapter<MuseumsAdapter.ViewHolder>() {

    var onItemClick : ((MuseumItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.museums_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMuseum = mList[position]
        holder.titleTextView.text = currentMuseum.title
        holder.descriptionTextView.text = currentMuseum.summary
        holder.ratingTextView.text = currentMuseum.reviews.ratingAverage.toString()

        val imagePath = "https://media.izi.travel/" + currentMuseum.contentProvider.uuid + "/" +currentMuseum.images[0].uuid + "_480x360.jpg"
        Picasso.get().load(imagePath).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentMuseum)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val titleTextView: TextView
        val descriptionTextView: TextView
        val ratingTextView: TextView
        val imageView: ImageView

        init {
            titleTextView = ItemView.findViewById(R.id.museum_title_textView)
            descriptionTextView = ItemView.findViewById(R.id.museum_description_textView)
            ratingTextView = ItemView.findViewById(R.id.star_textView)
            imageView = ItemView.findViewById(R.id.museum_row_imageView)
        }
    }
}