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
        binding.textViewDetailTankDesc.text = getString(R.string.tank_desc, tierTankDesc, nationTankDesc, typeTankDesc)
        binding.textViewDetailWinrate.text = getString(R.string.winrate,winrate)
        binding.textViewDetailTotalBattles.text = getString(R.string.battles,stats.battles.toString())
        binding.textViewDetailWins.text = getString(R.string.wins,stats.wins.toString())
        binding.textViewDetailLosses.text = getString(R.string.losses,stats.losses.toString())
        binding.textViewDetailAccuracy.text = getString(R.string.accuracy,(Math.round((stats.hits.toDouble()/stats.shots.toDouble())*10000)/100.toDouble()).toString())
        binding.textViewDetailDamageDealt.text = getString(R.string.damage_dealt,stats.damage_dealt.toString())
        binding.textViewDetailDamagedReceived.text = getString(R.string.damage_received,stats.damage_received.toString())
        binding.textViewDetailDamageRatio.text = getString(R.string.damage_ratio,(Math.round((stats.damage_dealt.toDouble()/stats.damage_received.toDouble())*100)/100.toDouble()).toString())
        binding.textViewDetailKills.text = getString(R.string.kills,stats.frags.toString())
        binding.textViewDetailDeaths.text = getString(R.string.deaths,(stats.battles-stats.survived_battles).toString())
        binding.textViewDetailKDR.text = getString(R.string.kill_death_ratio,(Math.round((stats.frags.toDouble()/(stats.battles-stats.survived_battles).toDouble())*100)/100.toDouble()).toString())
        binding.textViewDetailSurvivalRate.text = getString(R.string.survival_rate,(Math.round((stats.survived_battles.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString())
        binding.textViewDetailDamagePerGame.text = getString(R.string.damage_per_game,Math.round((stats.damage_dealt.toDouble()/(stats.battles).toDouble())).toString())
        binding.textViewDetailXpPerGame.text = getString(R.string.xp_per_game,Math.round((stats.xp.toDouble()/(stats.battles).toDouble())).toString())
    }
}