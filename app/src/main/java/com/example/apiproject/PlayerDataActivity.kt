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
        val winrate = (Math.round((stats.wins.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString()
        binding.textViewWinrate.text = "$winrate%"
        binding.textViewTotalBattles.text = "Battles: " + stats.battles.toString()
        binding.textViewWins.text = "Wins: " + stats.wins.toString()
        binding.textViewLosses.text = "Losses: " + stats.losses.toString()
        binding.textViewAccuracy.text = "Accuracy: " + (Math.round((stats.hits.toDouble()/stats.shots.toDouble())*10000)/100.toDouble()).toString() +"%"
        binding.textViewDamageDealt.text = "Damage Dealt: "+stats.damage_dealt
        binding.textViewDamagedReceived.text = "Damaged Received: "+stats.damage_received
        binding.textViewDamageRatio.text = "Damage Ratio: "+(Math.round((stats.damage_dealt.toDouble()/stats.damage_received.toDouble())*100)/100.toDouble()).toString()
        binding.textViewKills.text = "Kills: "+stats.frags
        binding.textViewDeaths.text = "Deaths: "+(stats.battles-stats.survived_battles).toString()
        binding.textViewKDR.text = "Kill Death Ratio: "+(Math.round((stats.frags.toDouble()/(stats.battles-stats.survived_battles).toDouble())*100)/100.toDouble()).toString()
        binding.textViewSurvivalRate.text = "Survival Rate: "+ (Math.round((stats.survived_battles.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString() +"%"
        binding.textViewDamagePerGame.text = "Damage per Game: "+Math.round((stats.damage_dealt.toDouble()/(stats.battles).toDouble())).toString()
        binding.textViewXpPerGame.text = "XP per Game: "+Math.round((stats.xp.toDouble()/(stats.battles).toDouble())).toString()
        binding.buttonGoToTankData.setOnClickListener {

        }
    }
}