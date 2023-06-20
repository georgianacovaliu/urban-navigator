package com.acs.urbannavigator.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.R
import com.acs.urbannavigator.models.countryModel.CountryItem
import com.squareup.picasso.Picasso

class CountriesAdapter(private val mList: List<CountryItem>):RecyclerView.Adapter<CountriesAdapter.ViewHolder>() {

    var onItemClick : ((CountryItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.countries_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = mList[position]
        holder.textView.text = currentCountry.title

        val imagePath = "https://flagsapi.com/" + currentCountry.countryCode.uppercase() + "/flat/64.png"
        Picasso.get().load(imagePath).into(holder.imageView)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentCountry)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val textView: TextView
        val imageView: ImageView

        init {
            // Define click listener for the ViewHolder's View
            textView = ItemView.findViewById(R.id.countryTextView)
            imageView = ItemView.findViewById(R.id.countryImageView)
        }
    }
}