package com.alphaStore.product.model.minified

import com.alphaStore.product.enums.DataStatus
import java.time.Instant

interface ProductListMinified {
    var id: String
    var productName: String
    var productPrice: Int
    var productsInStore: Int
    var productMainCategory: String?
    var productSubCategory: String?
    var createdDate: Instant
    var status: DataStatus
}