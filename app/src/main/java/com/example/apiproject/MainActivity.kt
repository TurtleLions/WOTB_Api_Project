package com.example.apiproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.covidtracker.RetrofitHelper
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "Main Activity"
    }
    var playerWrapper: PlayerWrapper? = null
    var playerData: PlayerData? = null
    var playerTankData: PlayerTankData? = null
    var tankData: TankData? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "u")
        getPlayerDataWhole()
    }
    private fun getPlayerDataWhole(){
        Log.d(TAG, "ran")
        GlobalScope.launch {
            getTankDataByApiCall(Constants.API_KEY)
            async {
                getPlayerWrapperByApiCall(
                    Constants.API_KEY,
                    "TurtleLions1"
                )
            }.await()
            while(playerWrapper==null){
            }
            Log.d(TAG, playerWrapper.toString())
            getPlayerDataByApiCall(Constants.API_KEY, playerWrapper!!.data[0].account_id)
            getPlayerTankDataByApiCall(Constants.API_KEY,playerWrapper!!.data[0].account_id)
            while(playerData==null||playerTankData==null||tankData==null){

            }
            Log.d("TAG", tankData.toString())
            Log.d(TAG, playerData.toString())
            Log.d(TAG,playerTankData.toString())
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
                    playerWrapper= response.body()
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
                    playerData = response.body()
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
                    playerTankData = response.body()
                }
            }

            override fun onFailure(call: Call<PlayerTankData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
    suspend fun getTankDataByApiCall(app_id: String){
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val tankDataCall = WOTBDataService.getTankData(app_id)
        tankDataCall.enqueue(object: Callback<TankData> {
            override fun onResponse(
                call: Call<TankData>,
                response: Response<TankData>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body()?.status=="ok"){
                    tankData = response.body()
                }
            }

            override fun onFailure(call: Call<TankData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}