package com.example.apiproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiproject.databinding.ActivityPlayerDataBinding
import com.example.apiproject.databinding.ActivityTankListBinding

class TankListActivity:AppCompatActivity() {
    companion object{
        val TAG = "TankListActivity"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
        val EXTRA_TANKDATA = "Tank Data"
    }
    private lateinit var binding: ActivityTankListBinding
    private lateinit var adapter: TankAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTankListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val playerWrapper =
            intent.getParcelableExtra<PlayerWrapper>(PlayerDataActivity.EXTRA_PLAYERWRAPPER)
        val playerData = intent.getParcelableExtra<PlayerData>(PlayerDataActivity.EXTRA_PLAYERDATA)
        val playerTankData =
            intent.getParcelableExtra<PlayerTankData>(PlayerDataActivity.EXTRA_PLAYERTANKDATA)
        val tankData = intent.getParcelableExtra<TankData>(PlayerDataActivity.EXTRA_TANKDATA)
        adapter = TankAdapter(playerTankData!!.data.get(playerWrapper!!.data[0].account_id)!!.toMutableList(),tankData!!)
        binding.recyclerviewTankList.adapter = adapter
        binding.recyclerviewTankList.layoutManager = LinearLayoutManager(this@TankListActivity)
    }
}