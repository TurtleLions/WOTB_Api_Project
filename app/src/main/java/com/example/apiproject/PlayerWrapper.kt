package com.example.apiproject
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlayerWrapper(val status: String?, val meta: Meta, val data: List<Player>):Parcelable {
    @Parcelize
    data class Meta(val count: Int):Parcelable{

    }
}