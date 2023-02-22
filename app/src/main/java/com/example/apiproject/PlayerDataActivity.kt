package com.example.apiproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityPlayerDataBinding

class PlayerDataActivity:AppCompatActivity() {
    companion object{
        val EXTRA_PLAYER = "Player"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
        val EXTRA_TANKDATA = "Tank Data"
    }
    private lateinit var player: Player
    private lateinit var playerWrapper: PlayerWrapper
    private lateinit var playerData: PlayerData
    private lateinit var playerTankData: PlayerTankData
    private lateinit var tankData: TankData
    private lateinit var binding: ActivityPlayerDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        player = intent.getParcelableExtra<Player>(MainActivity.EXTRA_PLAYER)!!
        playerWrapper = intent.getParcelableExtra<PlayerWrapper>(MainActivity.EXTRA_PLAYERWRAPPER)!!
        playerData = intent.getParcelableExtra<PlayerData>(MainActivity.EXTRA_PLAYERDATA)!!
        playerTankData = intent.getParcelableExtra<PlayerTankData>(MainActivity.EXTRA_PLAYERTANKDATA)!!
        tankData = intent.getParcelableExtra<TankData>(MainActivity.EXTRA_TANKDATA)!!
        val stats = playerData.data[0].statistics.all
        binding.textViewPlayerName.text = player.nickname
        binding.textViewWinrate.text = (stats.wins/stats.battles).toString()
    }
}