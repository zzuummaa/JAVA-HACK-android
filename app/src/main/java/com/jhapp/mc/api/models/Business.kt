package com.jhapp.mc.api.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Business(
    val id: Int,
    val name: String,
    val category: String,
    val iconURL: String,
    val profit: Chart,
    val revenue: Chart,
    val debts: Chart,
    val assets: Chart,
    val capital: Chart,
    val descriptionText: String

): Parcelable {
    companion object {
        const val CATEGORY_IT = "IT"
        const val CATEGORY_ENTERTAINMENT = "ENTERTAINMENT"
        const val CATEGORY_EDUCATION = "EDUCATION"
    }
}

@Parcelize
data class Chart(val x: List<Double>, val y: List<Double>): Parcelable

@Parcelize
data class Investement(val amount: Double, val business: Business): Parcelable