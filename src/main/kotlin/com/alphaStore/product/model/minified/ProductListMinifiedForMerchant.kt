package com.alphaStore.product.model.minified

import com.alphaStore.product.enums.DataStatus
import java.time.Instant

interface ProductListMinifiedForMerchant {
    var id: String
    var productName: String
    var productPrice: Int
    var numberOfProductsPresentAtStore: Int
    var productMainCategory: String?
    var productSubCategory: String?
    var createdDate: Instant
    var status: DataStatus
    var merchantId: String
}