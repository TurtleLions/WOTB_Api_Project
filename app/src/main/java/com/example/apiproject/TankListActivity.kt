package com.example.apiproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apiproject.databinding.ActivityTankListBinding
import com.example.covidtracker.RetrofitHelper
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
    private lateinit var playerTankData: PlayerTankData
    private lateinit var playerWrapper: PlayerWrapper
    private lateinit var playerData: PlayerData
    private lateinit var adapter: TankAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityTankListBinding.inflate(layoutInflater)
        setContentView(binding.root)
         playerTankData =
            intent.getParcelableExtra<PlayerTankData>(PlayerDataActivity.EXTRA_PLAYERTANKDATA)!!
        playerWrapper =
            intent.getParcelableExtra<PlayerWrapper>(PlayerDataActivity.EXTRA_PLAYERWRAPPER)!!
        playerData = intent.getParcelableExtra<PlayerData>(PlayerDataActivity.EXTRA_PLAYERDATA)!!
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
                Log.d(StartingActivity.TAG, "onResponse: ${response.body()}")
                if(response.body()?.status=="ok"){
                    tankData = response.body()!!
                }

                var array = playerTankData.data[playerWrapper.data[0].account_id]!!.toMutableList()
                val removeArray = mutableListOf<Int>()
                var index = 0
                for(vals in array){
                    if(tankData.data.get(vals.tank_id) ==null){
                        removeArray.add(0,index)
                    }
                    index++
                }
                for(indexes in removeArray){
                    array.removeAt(indexes)
                }
                array= array.sortedWith(compareByDescending<PlayerTankDataIndividual> {
                    tankData.data[it.tank_id]?.tier
                }.thenBy { tankData.data[it.tank_id]?.name }) as MutableList<PlayerTankDataIndividual>
                adapter = TankAdapter(array, tankData)
                binding.recyclerviewTankList.adapter = adapter
                binding.recyclerviewTankList.layoutManager = LinearLayoutManager(this@TankListActivity)

            }

            override fun onFailure(call: Call<TankData>, t: Throwable) {
                Log.d(StartingActivity.TAG, "onFailure: ${t.message}")
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.tank_list_menu, menu)

        return true
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {

            R.id.menu_Nation -> {
                adapter.playerTankList = adapter.playerTankList.sortedWith(compareBy<PlayerTankDataIndividual> {
                    tankData.data.get(it.tank_id)?.nation
                }.thenByDescending { tankData.data.get(it.tank_id)?.tier}.thenBy { tankData.data.get(it.tank_id)?.name }) as MutableList<PlayerTankDataIndividual>
                adapter.notifyDataSetChanged()
                true
            }
            R.id.menu_Tier -> {
                adapter.playerTankList = adapter.playerTankList.sortedWith(compareByDescending<PlayerTankDataIndividual> {
                    tankData.data.get(it.tank_id)?.tier
                }.thenBy { tankData.data.get(it.tank_id)?.name }) as MutableList<PlayerTankDataIndividual>
                adapter.notifyDataSetChanged()
                true
            }
            R.id.menu_Winrate -> {
                adapter.playerTankList = adapter.playerTankList.sortedWith(compareByDescending<PlayerTankDataIndividual> {
                    when(it.all.battles){
                        0 -> 0.toDouble()
                        else -> (Math.round((it.all.wins.toDouble() / it.all.battles.toDouble()) * 10000) / 100.toDouble())
                    }
                }.thenBy { tankData.data.get(it.tank_id)?.name })as MutableList<PlayerTankDataIndividual>
                adapter.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}