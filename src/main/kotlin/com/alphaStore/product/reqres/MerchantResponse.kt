package com.alphaStore.product.reqres

import com.alphaStore.product.enums.DataStatus
import java.time.Instant

data class MerchantResponse (
    val id: String,
    val merchantName: String,
    val merchantAddress: String,
    val merchantPhone: String,
    val merchantCountry: String,
    val merchantEmail: String,
    val products: ArrayList<String>,
    val status: DataStatus,
)