package com.example.apiproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityPlayerDataBinding

class PlayerDataActivity:AppCompatActivity() {
    companion object{
        val TAG = "PlayerDataActivity"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
        val EXTRA_TANKDATA = "Tank Data"
    }
    private lateinit var binding: ActivityPlayerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val playerWrapper = intent.getParcelableExtra<PlayerWrapper>(MainActivity.EXTRA_PLAYERWRAPPER)
        val playerData = intent.getParcelableExtra<PlayerData>(MainActivity.EXTRA_PLAYERDATA)
        val playerTankData = intent.getParcelableExtra<PlayerTankData>(MainActivity.EXTRA_PLAYERTANKDATA)
        val TankData = intent.getParcelableExtra<TankData>(MainActivity.EXTRA_TANKDATA)
        Log.d(TAG, playerData.toString())

        val stats = playerData!!.data[playerWrapper!!.data[0].account_id]!!.statistics.all
        Log.d(TAG, stats.toString())
        binding.textViewPlayerName.text = playerWrapper.data[0].nickname
        binding.textViewWinrate.text = (Math.round((stats.wins.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString()
    }
}