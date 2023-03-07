package com.example.apiproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityTankDetailBinding
import com.squareup.picasso.Picasso

class TankDetailActivity:AppCompatActivity(){
    companion object{
        val EXTRA_CURRENTTANKDATA = "current tank data"
        val EXTRA_PLAYERTANKDATA = "player tank data"
    }
    private lateinit var binding: ActivityTankDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTankDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentTankData = intent.getParcelableExtra<TankData.Tank>(TankAdapter.EXTRA_CURRENTTANKDATA)
        val playerTankDataIndividual = intent.getParcelableExtra<PlayerTankDataIndividual>(TankAdapter.EXTRA_PLAYERTANKDATA)
        val stats = playerTankDataIndividual!!.all
        val winrate = (Math.round((stats.wins.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString()
        Picasso.get()
            .load(currentTankData!!.images.preview.replace("http","https"))
            .resize(100, 100)
            .centerInside()
            .into(binding.imageViewTank)
        binding.textViewDetailTankName.text = currentTankData.name
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
        binding.textViewDetailTankDesc.text = tierTankDesc + nationTankDesc + typeTankDesc
        binding.textViewDetailWinrate.text = "$winrate%"
        binding.textViewDetailTotalBattles.text = "Battles: " + stats.battles.toString()
        binding.textViewDetailWins.text = "Wins: " + stats.wins.toString()
        binding.textViewDetailLosses.text = "Losses: " + stats.losses.toString()
        binding.textViewDetailAccuracy.text = "Accuracy: " + (Math.round((stats.hits.toDouble()/stats.shots.toDouble())*10000)/100.toDouble()).toString() +"%"
        binding.textViewDetailDamageDealt.text = "Damage Dealt: "+stats.damage_dealt
        binding.textViewDetailDamagedReceived.text = "Damaged Received: "+stats.damage_received
        binding.textViewDetailDamageRatio.text = "Damage Ratio: "+(Math.round((stats.damage_dealt.toDouble()/stats.damage_received.toDouble())*100)/100.toDouble()).toString()
        binding.textViewDetailKills.text = "Kills: "+stats.frags
        binding.textViewDetailDeaths.text = "Deaths: "+(stats.battles-stats.survived_battles).toString()
        binding.textViewDetailKDR.text = "Kill Death Ratio: "+(Math.round((stats.frags.toDouble()/(stats.battles-stats.survived_battles).toDouble())*100)/100.toDouble()).toString()
        binding.textViewDetailSurvivalRate.text = "Survival Rate: "+ (Math.round((stats.survived_battles.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString() +"%"
        binding.textViewDetailDamagePerGame.text = "Damage per Game: "+Math.round((stats.damage_dealt.toDouble()/(stats.battles).toDouble())).toString()
        binding.textViewDetailXpPerGame.text = "XP per Game: "+Math.round((stats.xp.toDouble()/(stats.battles).toDouble())).toString()
    }
}