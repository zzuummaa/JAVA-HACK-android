package com.jhapp.mc.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChatMessage(
    val id: Int,
    val body: String,
    val date: String,
    val bot: Boolean
): Parcelable