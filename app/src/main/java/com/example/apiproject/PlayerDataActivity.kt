package com.example.apiproject

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityPlayerDataBinding

class PlayerDataActivity:AppCompatActivity() {
    companion object{
        val TAG = "PlayerDataActivity"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
    }
    private lateinit var binding: ActivityPlayerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val playerWrapper = intent.getParcelableExtra<PlayerWrapper>(StartingActivity.EXTRA_PLAYERWRAPPER)
        val playerData = intent.getParcelableExtra<PlayerData>(StartingActivity.EXTRA_PLAYERDATA)
        val playerTankData = intent.getParcelableExtra<PlayerTankData>(StartingActivity.EXTRA_PLAYERTANKDATA)
        val stats = playerData!!.data[playerWrapper!!.data[0].account_id]!!.statistics.all
        binding.textViewPlayerName.text = playerWrapper.data[0].nickname
        val winrate = (Math.round((stats.wins.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString()
        binding.textViewWinrate.text = getString(R.string.winrate,winrate)
        binding.textViewTotalBattles.text = getString(R.string.battles,stats.battles.toString())
        binding.textViewWins.text = getString(R.string.wins,stats.wins.toString())
        binding.textViewLosses.text = getString(R.string.losses,stats.losses.toString())
        binding.textViewAccuracy.text = getString(R.string.accuracy,(Math.round((stats.hits.toDouble()/stats.shots.toDouble())*10000)/100.toDouble()).toString())
        binding.textViewDamageDealt.text = getString(R.string.damage_dealt,stats.damage_dealt.toString())
        binding.textViewDamagedReceived.text = getString(R.string.damage_received,stats.damage_received.toString())
        binding.textViewDamageRatio.text = getString(R.string.damage_ratio,(Math.round((stats.damage_dealt.toDouble()/stats.damage_received.toDouble())*100)/100.toDouble()).toString())
        binding.textViewKills.text = getString(R.string.kills,stats.frags.toString())
        binding.textViewDeaths.text = getString(R.string.deaths,(stats.battles-stats.survived_battles).toString())
        binding.textViewKDR.text = getString(R.string.kill_death_ratio,(Math.round((stats.frags.toDouble()/(stats.battles-stats.survived_battles).toDouble())*100)/100.toDouble()).toString())
        binding.textViewSurvivalRate.text = getString(R.string.survival_rate,(Math.round((stats.survived_battles.toDouble()/stats.battles.toDouble())*10000)/100.toDouble()).toString())
        binding.textViewDamagePerGame.text = getString(R.string.damage_per_game,Math.round((stats.damage_dealt.toDouble()/(stats.battles).toDouble())).toString())
        binding.textViewXpPerGame.text = getString(R.string.xp_per_game,Math.round((stats.xp.toDouble()/(stats.battles).toDouble())).toString())
        binding.buttonGoToTankData.setOnClickListener {
            val tankListIntent = Intent(this@PlayerDataActivity, TankListActivity::class.java)
            tankListIntent.putExtra(PlayerDataActivity.EXTRA_PLAYERWRAPPER, playerWrapper)
            tankListIntent.putExtra(PlayerDataActivity.EXTRA_PLAYERDATA, playerData)
            tankListIntent.putExtra(PlayerDataActivity.EXTRA_PLAYERTANKDATA, playerTankData)
            startActivity(tankListIntent)
        }
    }
}