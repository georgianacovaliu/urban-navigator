package com.acs.urbannavigator.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.R
import com.acs.urbannavigator.models.cityModel.CityItem
import com.squareup.picasso.Picasso

class CitiesAdapter(private val mList: List<CityItem>):RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    var onItemClick : ((CityItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cities_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCity = mList[position]
        holder.textView.text = currentCity.title
        if (currentCity.images != null){
            val imagePath = "https://media.izi.travel/cities/" + currentCity.uuid + "/" + currentCity.images[0].uuid + ".jpg"
            Log.d("sufar", imagePath)
            Picasso.get().load(imagePath).into(holder.imageView)
        }

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentCity)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val textView: TextView
        val imageView: ImageView

        init {
            textView = ItemView.findViewById(R.id.cityTextView)
            imageView = ItemView.findViewById(R.id.city_imageView)
        }
    }
}