package com.jhapp.mc.api.models

class InvestRequestBody(val amount: Double, val business_id: Int)

data class InvestResp(
    val id: Int,
    val amount: Double,
    val business_id: Int
)