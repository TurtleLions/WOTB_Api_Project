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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun getPlayerWrapperByApiCall(state: String) {
        val covidDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val countyDataCall = covidDataService.getPlayerWrapper(state,
            SyncStateContract.Constants.API_KEY)
        countyDataCall.enqueue(object: Callback<List<CountyData>> {
            override fun onResponse(
                call: Call<List<CountyData>>,
                response: Response<List<CountyData>>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if(response.body()!=null)
                    adapter = CountyAdapter(response.body()!!)
                else{
                    Log.d(TAG, "response is null")
                }
                binding.recyclerviewCountyList.adapter = adapter
                binding.recyclerviewCountyList.layoutManager = LinearLayoutManager(this@CountyListActivity)
            }

            override fun onFailure(call: Call<List<CountyData>>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}