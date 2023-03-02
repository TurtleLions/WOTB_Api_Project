package com.example.apiproject

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiproject.databinding.ActivityPlayerDataBinding
import com.example.apiproject.databinding.ActivityTankListBinding
import com.example.covidtracker.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TankListActivity:AppCompatActivity() {
    companion object{
        val TAG = "TankListActivity"
        val EXTRA_PLAYERWRAPPER = "Player Wrapper"
        val EXTRA_PLAYERDATA = "Player Data"
        val EXTRA_PLAYERTANKDATA = "Player Tank Data"
        val EXTRA_TANKDATA = "Tank Data"
    }
    private lateinit var binding: ActivityTankListBinding
    private lateinit var tankData: TankData
    private lateinit var adapter: TankAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTankListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getTankDataByApiCall(Constants.API_KEY)


    }
    fun getTankDataByApiCall(app_id: String){
        val WOTBDataService = RetrofitHelper.getInstance().create(WOTBDataService::class.java)
        val tankDataCall = WOTBDataService.getTankData(app_id)
        tankDataCall.enqueue(object: Callback<TankData> {
            override fun onResponse(
                call: Call<TankData>,
                response: Response<TankData>
            ) {
                Log.d(MainActivity.TAG, "onResponse: ${response.body()}")
                if(response.body()?.status=="ok"){
                    tankData = response.body()!!
                }
                val playerWrapper =
                    intent.getParcelableExtra<PlayerWrapper>(PlayerDataActivity.EXTRA_PLAYERWRAPPER)
                val playerData = intent.getParcelableExtra<PlayerData>(PlayerDataActivity.EXTRA_PLAYERDATA)
                val playerTankData =
                    intent.getParcelableExtra<PlayerTankData>(PlayerDataActivity.EXTRA_PLAYERTANKDATA)
                val array = playerTankData!!.data.get(playerWrapper!!.data[0].account_id)!!.toMutableList()
                val removeArray = mutableListOf<Int>()
                var index = 0
                for(vals in array){
                    if(tankData?.data?.get(vals.tank_id) ==null){
                        removeArray.add(0,index)
                    }
                    index++
                }
                for(indexes in removeArray){
                    array.removeAt(indexes)
                }
                adapter = TankAdapter(array,tankData!!)
                binding.recyclerviewTankList.adapter = adapter
                binding.recyclerviewTankList.layoutManager = LinearLayoutManager(this@TankListActivity)

            }

            override fun onFailure(call: Call<TankData>, t: Throwable) {
                Log.d(MainActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
}