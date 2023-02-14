package com.example.apiproject

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WOTBDataService {
    @GET("account/list/?application_id={app_id}&search={playerName}.json")
    fun getPlayerWrapper(
        @Path("app_id") app_id: String,
        @Path("playerName") playerName: String
    ) : Call<PlayerWrapper>
}