package com.example.apiproject

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityMainBinding
import com.example.covidtracker.RetrofitHelper
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartingActivity : AppCompatActivity() {
    companion object{
        const val TAG = "Main Activity"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
    }
    lateinit var playerName: String
    lateinit var playerWrapper: PlayerWrapper
    lateinit var playerData: PlayerData
    lateinit var playerTankData: PlayerTankData
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainSubmitButton.setOnClickListener {
            playerName= binding.mainEditText.text.toString()
            getPlayerDataWhole()

        }
    }

    private fun getPlayerDataWhole(){
        GlobalScope.launch {
            async {
                getPlayerWrapperByApiCall(Constants.API_KEY, playerName)
            }.await()
            while(!this@StartingActivity::playerWrapper.isInitialized){
            }
            getPlayerDataByApiCall(Constants.API_KEY, playerWrapper.data[0].account_id)
            getPlayerTankDataByApiCall(Constants.API_KEY,playerWrapper.data[0].account_id)
            while(!this@StartingActivity::playerData.isInitialized||!this@StartingActivity::playerTankData.isInitialized){

            }
            val playerDataActivityIntent = Intent(this@StartingActivity, PlayerDataActivity::class.java)
            playerDataActivityIntent.putExtra(EXTRA_PLAYERWRAPPER, playerWrapper)
            playerDataActivityIntent.putExtra(EXTRA_PLAYERDATA, playerData)
            playerDataActivityIntent.putExtra(EXTRA_PLAYERTANKDATA, playerTankData)
            startActivity(playerDataActivityIntent)
        }

    }
    suspend fun getPlayerWrapperByApiCall(app_id: String, playerName: String) {
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val playerWrapperCall = WOTBDataService.getPlayerWrapper(app_id,
            playerName)
        var returned:PlayerWrapper? = null
        playerWrapperCall.enqueue(object: Callback<PlayerWrapper> {
            override fun onResponse(
                call: Call<PlayerWrapper>,
                response: Response<PlayerWrapper>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body()?.status =="ok") {
                    playerWrapper= response.body()!!
                }
            }

            override fun onFailure(call: Call<PlayerWrapper>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
    suspend fun getPlayerDataByApiCall(app_id: String, playerID: Int) {
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val playerDataCall = WOTBDataService.getPlayerData(app_id,
            playerID)
        playerDataCall.enqueue(object: Callback<PlayerData> {
            override fun onResponse(
                call: Call<PlayerData>,
                response: Response<PlayerData>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body()?.status=="ok"){
                    playerData = response.body()!!
                }
            }

            override fun onFailure(call: Call<PlayerData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
    suspend fun getPlayerTankDataByApiCall(app_id: String, playerID: Int){
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val playerTankDataCall = WOTBDataService.getPlayerTankData(app_id,
            playerID)
        playerTankDataCall.enqueue(object: Callback<PlayerTankData> {
            override fun onResponse(
                call: Call<PlayerTankData>,
                response: Response<PlayerTankData>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body()?.status=="ok"){
                    playerTankData = response.body()!!
                }
            }

            override fun onFailure(call: Call<PlayerTankData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}