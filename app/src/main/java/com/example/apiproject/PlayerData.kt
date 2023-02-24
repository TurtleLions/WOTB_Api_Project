package com.example.apiproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerData(val status: String?, val data: Map<Int, IDData>):Parcelable{
    @Parcelize
    data class IDData(val statistics: Statistics, val account_id: Int, val nickname: String):Parcelable{
        @Parcelize
        data class Statistics(val all: All):Parcelable{
            @Parcelize
            data class All(
                val spotted: Int,
                val max_frags_tank_id: Int,
                val hits: Int,
                val frags: Int,
                val max_xp:Int,
                val max_xp_tank_id:Int,
                val wins:Int,
                val losses:Int,
                val capture_points:Int,
                val battles: Int,
                val damage_dealt:Int,
                val damage_received:Int,
                val max_frags: Int,
                val shots:Int,
                val frags8p:Int,
                val xp:Int,
                val win_and_survived:Int,
                val survived_battles:Int,
                val dropped_capture_points: Int
            ):Parcelable{

            }
        }


    }
}
