package com.example.apiproject
import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TankData(val status: String?, val data: Map<Int, Tank>):Parcelable{
    @Parcelize
    data class Tank(val description: String?, val nation: String, val is_premium: Boolean, val images:TankImage, val tier: Int, val tank_id:Int, val type:String, val name:String):Parcelable{
        @Parcelize
        data class TankImage(val preview: String, val normal:String):Parcelable
    }
}
