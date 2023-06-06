package com.acs.urbannavigator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.models.CountryItem
import com.acs.urbannavigator.models.Museum.MuseumItem
import com.acs.urbannavigator.models.Tour.TourItem
import com.squareup.picasso.Picasso

class ToursAdapter(private val mList: List<TourItem>):RecyclerView.Adapter<ToursAdapter.ViewHolder>() {

    var onItemClick : ((TourItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tours_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTour = mList[position]
        holder.titleTextView.text = currentTour.title
        holder.descriptionTextView.text = currentTour.summary
        holder.ratingTextView.text = currentTour.reviews.ratingAverage.toString()
        holder.clockTextView.text = (currentTour.duration/60).toString()

        val imagePath = "https://media.izi.travel/" + currentTour.contentProvider.uuid + "/" +currentTour.images[0].uuid + "_480x360.jpg"
        Picasso.get().load(imagePath).into(holder.imageView)

        when (currentTour.category) {
            "bike" -> holder.typImageView.setImageResource(R.drawable.ic_bike)
            "bus" -> holder.typImageView.setImageResource(R.drawable.ic_bus)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentTour)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val titleTextView: TextView
        val descriptionTextView: TextView
        val ratingTextView: TextView
        val imageView: ImageView
        val typImageView: ImageView
        val clockTextView: TextView

        init {
            titleTextView = ItemView.findViewById(R.id.tour_title_textView)
            descriptionTextView = ItemView.findViewById(R.id.tour_description_textView)
            ratingTextView = ItemView.findViewById(R.id.tour_star_textView)
            imageView = ItemView.findViewById(R.id.tour_row_imageView)
            typImageView = ItemView.findViewById(R.id.tour_type_imageView)
            clockTextView = ItemView.findViewById(R.id.clock_textView)
        }
    }
}