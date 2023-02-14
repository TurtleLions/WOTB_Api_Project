package com.example.apiproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerTankData(val status:String, val data: Map<Int, PlayerData.Statistics.All>, val account_id:Int, val tank_id:Int):Parcelable{
    @Parcelize
    data class All(
        val spotted: Int,
        val hits: Int,
        val frags:Int,
        val max_xp:Int,
        val wins:Int,
        val losses:Int,
        val capture_points:Int,
        val battles:Int,
        val damage_dealt:Int,
        val damage_received: Int,
        val max_frags:Int,
        val shots:Int,
        val frags8p:Int,
        val xp:Int,
        val win_and_survived:Int,
        val survived_battles:Int,
        val dropped_capture_points:Int
        ):Parcelable{

    }
}
