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
import com.acs.urbannavigator.models.TourList.TourListItem
import com.squareup.picasso.Picasso

class TourListAdapter(private val mList: List<TourListItem>):RecyclerView.Adapter<TourListAdapter.ViewHolder>() {

    var onItemClick : ((TourListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tour_list_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTourItem = mList[position]
        holder.indexTextView.text = (position + 1).toString()
        holder.titleTextView.text = currentTourItem.content[0].title

        if (!currentTourItem.content[0].images.isNullOrEmpty()){
            val imagePath = "https://media.izi.travel/" + currentTourItem.contentProvider.uuid + "/" +currentTourItem.content[0].images[0].uuid + "_240x180.jpg"
            Log.d("sufar", imagePath)
            Picasso.get().load(imagePath).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentTourItem)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val indexTextView: TextView
        val imageView: ImageView
        val titleTextView: TextView

        init {
            indexTextView = ItemView.findViewById(R.id.tourListIndex)
            imageView = ItemView.findViewById(R.id.tourListImageView)
            titleTextView = ItemView.findViewById(R.id.tourListTextView)
        }
    }
}