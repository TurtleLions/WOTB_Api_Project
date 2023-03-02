package com.example.apiproject

import android.content.Context
import android.content.Intent
import android.icu.lang.UCharacter.JoiningGroup.TAH
import android.os.Binder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.apiproject.databinding.ActivityMainBinding
import com.example.covidtracker.RetrofitHelper
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
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
        Log.d(TAG, "u")
        binding.mainSubmitButton.setOnClickListener {
            playerName= binding.mainEditText.text.toString()
            getPlayerDataWhole()

        }
    }

    private fun getPlayerDataWhole(){
        Log.d(TAG, "ran")
        GlobalScope.launch {
            //getTankDataByApiCall(Constants.API_KEY)
            async {
                getPlayerWrapperByApiCall(Constants.API_KEY, playerName)
            }.await()
            while(!this@MainActivity::playerWrapper.isInitialized){
            }
            Log.d(TAG, playerWrapper.toString())
            getPlayerDataByApiCall(Constants.API_KEY, playerWrapper.data[0].account_id)
            getPlayerTankDataByApiCall(Constants.API_KEY,playerWrapper.data[0].account_id)
            while(!this@MainActivity::playerData.isInitialized||!this@MainActivity::playerTankData.isInitialized){

            }

            Log.d(TAG, playerData.toString())
            Log.d(TAG,playerTankData.toString())
            val playerDataActivityIntent = Intent(this@MainActivity, PlayerDataActivity::class.java)
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