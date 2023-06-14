package com.acs.urbannavigator

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.models.MuseumDetails.Children
import com.squareup.picasso.Picasso

class MuseumDetailsAdapter(private val mList: List<Children>):RecyclerView.Adapter<MuseumDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.museum_details_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentExhibit = mList[position]
        holder.titleTextView.text = currentExhibit.title
        Log.d("exhibit", currentExhibit.title)

        val imagePath = "https://media.izi.travel/" + currentExhibit.contentProvider.uuid + "/" +currentExhibit.images[0].uuid + "_480x360.jpg"
        Picasso.get().load(imagePath).into(holder.imageView)

    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val titleTextView: TextView
        val imageView: ImageView

        init {
            titleTextView = ItemView.findViewById(R.id.exhibit_title)
            imageView = ItemView.findViewById(R.id.exhibit_image)
        }
    }
}