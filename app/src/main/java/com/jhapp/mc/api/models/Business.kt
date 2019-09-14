package com.jhapp.mc.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Business(
    val id: Int,
    val name: String,
    val category: String,
    val iconURL: String
): Parcelable {
    companion object {
        const val CATEGORY_IT = "IT"
        const val CATEGORY_ENTERTAINMENT = "ENTERTAINMENT"
        const val CATEGORY_EDUCATION = "EDUCATION"
    }
}