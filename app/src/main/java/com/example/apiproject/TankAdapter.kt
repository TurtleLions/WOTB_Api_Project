package com.example.apiproject

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TankAdapter(var playerTankList: MutableList<PlayerTankDataIndividual>, var tankData:TankData):RecyclerView.Adapter<TankAdapter.ViewHolder>() {
    companion object{
        val TAG = "Tank Adapter"
        val EXTRA_CURRENTTANKDATA = "current tank data"
        val EXTRA_PLAYERTANKDATA = "player tank data"
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
        val imageViewPicture: ImageView
        val layout: ConstraintLayout

        init {
            textViewName = view.findViewById(R.id.tank_item_name)
            textViewWinrate = view.findViewById(R.id.tank_item_winrate)
            textViewTankDesc = view.findViewById(R.id.tank_item_tankdesc)
            textViewPremium = view.findViewById(R.id.tank_item_premium)
            imageViewPicture = view.findViewById(R.id.tank_item_imageView)
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
        Picasso.get()
            .load(currentTankData!!.images.preview.replace("http","https"))
            .resize(100, 100)
            .centerInside()
            .into(viewHolder.imageViewPicture)
        viewHolder.textViewName.text = currentTankData.name
        viewHolder.textViewWinrate.text =context.getString(R.string.winrate,(Math.round((playerTankList[position].all.wins.toDouble() / playerTankList[position].all.battles.toDouble()) * 10000) / 100.toDouble()).toString())
        val tierTankDesc = "Tier " + currentTankData.tier
        val nationTankDesc = when (currentTankData.nation) {
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
        val typeTankDesc = when (currentTankData.type) {
            "heavyTank" -> "Heavy Tank"
            "mediumTank" -> "Medium Tank"
            "lightTank" -> "Light Tank"
            "AT-SPG" -> "Tank Destroyer"
            else -> "Tank"
        }
        viewHolder.textViewTankDesc.text = context.getString(R.string.tank_desc, tierTankDesc, nationTankDesc, typeTankDesc)
        viewHolder.textViewPremium.text = when (currentTankData.is_premium) {
            true -> "Premium Tank"
            else -> "Tech Tree Tank"
        }
        viewHolder.layout.setOnClickListener {
            val detailActivity = Intent(it.context, TankDetailActivity::class.java).apply {
                putExtra(EXTRA_CURRENTTANKDATA, currentTankData)
                putExtra(EXTRA_PLAYERTANKDATA, playerTankList[position])
            }
            it.context.startActivity(detailActivity)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = playerTankList.size
}