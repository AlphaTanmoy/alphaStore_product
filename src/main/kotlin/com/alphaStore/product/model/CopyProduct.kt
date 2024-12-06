package com.alphaStore.product.model

import com.alphaStore.product.enums.ProductMainCategory
import com.alphaStore.product.enums.ProductSubCategory

data class CopyProduct (
    var productName: String,
    var productPrice: Long,
    var numberOfProductsPresentAtStore: Long,
    var numberOfProductsEnteredByMerchant: Long,
    var productMainCategory: ProductMainCategory,
    var productSubCategory: ProductSubCategory,
    var merchantId: String,
    var batchId: String,
    var uniqueId: String,
    var id: String
)