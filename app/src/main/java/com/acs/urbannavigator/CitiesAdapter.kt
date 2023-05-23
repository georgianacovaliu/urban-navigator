package com.acs.urbannavigator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.models.CityItem

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

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentCity)
        }
    }

    class ViewHolder(ItemView: View): RecyclerView.ViewHolder(ItemView){
        val textView: TextView

        init {
            textView = ItemView.findViewById(R.id.cityTextView)
        }
    }
}