import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acs.urbannavigator.R
import com.acs.urbannavigator.models.TourList.TourListItem
import com.squareup.picasso.Picasso

class TourListAdapter(private val mList: List<TourListItem>): RecyclerView.Adapter<TourListAdapter.ViewHolder>() {

    var onItemClick: ((TourListItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tour_list_row_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTourItem = mList[position]
        holder.bind(currentTourItem, position)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(currentTourItem)
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val indexTextView: TextView = itemView.findViewById(R.id.tourListIndex)
        private val imageView: ImageView = itemView.findViewById(R.id.tourListImageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.tourListTextView)
        private val dotsView: View = itemView.findViewById(R.id.dotsView)

        fun bind(tourItem: TourListItem, position: Int) {
            indexTextView.text = (position + 1).toString()
            titleTextView.text = tourItem.content[0].title

            if (!tourItem.content[0].images.isNullOrEmpty()) {
                val imagePath = "https://media.izi.travel/${tourItem.contentProvider.uuid}/${tourItem.content[0].images[0].uuid}_240x180.jpg"
                Picasso.get().load(imagePath).into(imageView)
            }

            dotsView.visibility = if (position == itemCount - 1) View.GONE else View.VISIBLE
        }
    }
}
