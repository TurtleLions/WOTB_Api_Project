package com.example.apiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.covidtracker.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    companion object{
        const val TAG = "Main Activity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getPlayerWrapperByApiCall(Constants.API_KEY, "TurtleLions1")
    }
    private fun getPlayerWrapperByApiCall(app_id: String, playerName: String) {
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val playerWrapperCall = WOTBDataService.getPlayerWrapper(app_id,
            playerName)
        playerWrapperCall.enqueue(object: Callback<PlayerWrapper> {
            override fun onResponse(
                call: Call<PlayerWrapper>,
                response: Response<PlayerWrapper>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<PlayerWrapper>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
    private fun getPlayerDataByApiCall(app_id: String, playerID: Int) {
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val playerDataCall = WOTBDataService.getPlayerData(app_id,
            playerID)
        playerDataCall.enqueue(object: Callback<PlayerData> {
            override fun onResponse(
                call: Call<PlayerData>,
                response: Response<PlayerData>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
            }

            override fun onFailure(call: Call<PlayerData>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}