package com.example.apiproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WOTBDataService {
    @GET("account/list/")
    fun getPlayerWrapper(
        @Query("application_id") app_id: String,
        @Query("search") playerName: String
    ) : Call<PlayerWrapper>
    @GET("account/info/")
    fun getPlayerData(
        @Query("application_id") app_id: String,
        @Query("account_id") account_id: Int
    ) :Call<PlayerData>
    @GET("tanks/stats/")
    fun getPlayerTankData(
        @Query("application_id") app_id: String,
        @Query("account_id") account_id: Int
    ):Call<PlayerTankData>
    @GET("encyclopedia/vehicles/")
    fun getTankData(
        @Query("application_id") app_id: String
    ):Call<TankData>
}