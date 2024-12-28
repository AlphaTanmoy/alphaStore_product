package com.alphaStore.product.model

import com.alphaStore.product.enums.ProductMainCategory
import com.alphaStore.product.enums.ProductSellingStatus
import com.alphaStore.product.enums.ProductSubCategory

data class CopyProduct (
    var productName: String,
    var productPrice: Long,
    var productInStore: Long,
    var productMainCategory: ProductMainCategory,
    var productSubCategory: ProductSubCategory,
    var merchantId: String,
    var batchGroup: String,
    var id: String,
    var companyName: String
)