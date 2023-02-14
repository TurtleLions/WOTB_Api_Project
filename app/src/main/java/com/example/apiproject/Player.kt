package com.example.apiproject

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Player(val nickname: String, val account_id: Int):Parcelable {
}