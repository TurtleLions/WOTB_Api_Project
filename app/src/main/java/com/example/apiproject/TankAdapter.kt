package com.example.apiproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TankAdapter {
    companion object{
        val TAG = "hi"
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewCounty: TextView
        val textViewLastUpdated: TextView
        val textViewWeeklyCases: TextView
        val layout: ConstraintLayout

        init {
            textViewCounty = view.findViewById(R.id.recycler_county)
            textViewLastUpdated = view.findViewById(R.id.recycler_last_updated)
            textViewWeeklyCases = view.findViewById(R.id.recycler_weekly)
            layout = view.findViewById(R.id.ConstraintLayout)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_county_data, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.textViewWeeklyCases.context
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textViewCounty.text = dataSet[position].county
        viewHolder.textViewLastUpdated.text = dataSet[position].lastUpdatedDate
        viewHolder.textViewWeeklyCases.text = dataSet[position].metrics.weeklyNewCasesPer100k.toString()
        when(dataSet[position].cdcTransmissionLevel){
            0->{
                context.resources.getColor(R.color.low_transmission,context.theme)
                viewHolder.textViewCounty.setTextColor(context.resources.getColor(R.color.low_transmission))
                viewHolder.textViewCounty.setCompoundDrawablesRelativeWithIntrinsicBounds(0,0,0,0)
            }
            1->{
                context.resources.getColor(R.color.moderate_transmission,context.theme)
                viewHolder.textViewCounty.setTextColor(context.resources.getColor(R.color.black))
                viewHolder.textViewCounty.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_moderate_24,0,0,0)
            }
            2->{
                context.resources.getColor(R.color.substantial_transmission,context.theme)
                viewHolder.textViewCounty.setTextColor(context.resources.getColor(R.color.substantial_transmission))
                viewHolder.textViewCounty.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_substantial_24,0,0,0)
            }
            3->{
                context.resources.getColor(R.color.high_transmission,context.theme)
                viewHolder.textViewCounty.setTextColor(context.resources.getColor(R.color.high_transmission))
                viewHolder.textViewCounty.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_high_24,0,0,0)
            }
        }
        viewHolder.layout.setOnClickListener{
            val detailActivity = Intent(it.context, CountyDetailActivity::class.java).apply {
                putExtra(EXTRA_COUNTY, dataSet[position])
            }
            it.context.startActivity(detailActivity)

        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}