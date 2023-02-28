package com.example.apiproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class TankAdapter(var playerTankList: MutableList<PlayerTankDataIndividual>, var tankData:TankData):RecyclerView.Adapter<TankAdapter.ViewHolder>() {
    companion object{
        val TAG = "hi"
        val EXTRA_COUNTY = "county"
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView
        val textViewWinrate: TextView
        val textViewTankDesc: TextView
        val textViewPremium: TextView
        val layout: ConstraintLayout

        init {
            textViewName = view.findViewById(R.id.tank_item_name)
            textViewWinrate = view.findViewById(R.id.tank_item_winrate)
            textViewTankDesc = view.findViewById(R.id.tank_item_tankdesc)
            textViewPremium = view.findViewById(R.id.tank_item_premium)
            layout = view.findViewById(R.id.ConstraintLayout)
        }
    }
    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_tank_detail, viewGroup, false)

        return ViewHolder(view)
    }
    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val context = viewHolder.textViewName.context
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val currentTankData = tankData.data[playerTankList[position].tank_id]
        if(currentTankData == null){
            playerTankList.removeAt(position)
        }
        viewHolder.textViewName.text = currentTankData?.name
        viewHolder.textViewWinrate.text =
            (Math.round((playerTankList[position].all.wins.toDouble() / playerTankList[position].all.battles.toDouble()) * 10000) / 100.toDouble()).toString()
        val tierTankDesc = "Tier " + currentTankData?.tier
        val nationTankDesc = when (currentTankData?.nation) {
            "usa" -> " American "
            "uk" -> " British "
            "germany" -> " German "
            "ussr" -> " Soviet "
            "france" -> " French "
            "japan" -> " Japanese "
            "china" -> " Chinese "
            "european" -> " European "
            else -> " Hybrid "
        }
        val typeTankDesc = when (currentTankData?.type) {
            "heavyTank" -> "Heavy Tank"
            "mediumTank" -> "Medium Tank"
            "lightTank" -> "Light Tank"
            "AT-SPG" -> "Tank Destroyer"
            else -> "Tank"
        }
        viewHolder.textViewTankDesc.text = tierTankDesc + nationTankDesc + typeTankDesc
        viewHolder.textViewPremium.text = when (currentTankData?.is_premium) {
            true -> "Premium Tank"
            else -> "Tech Tree Tank"
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = playerTankList.size
}