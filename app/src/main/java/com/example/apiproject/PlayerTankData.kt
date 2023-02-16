package com.example.apiproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerTankData(val status:String, val data: Map<Int, List<PlayerTankDataIndividual>>):Parcelable{

}
