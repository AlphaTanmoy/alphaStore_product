package com.alphaStore.product.model.minifiedImpl

import com.alphaStore.product.model.minified.ProductListMinified
import com.alphaStore.product.enums.DataStatus
import java.time.Instant

data class ProductListMinifiedImpl(
    override var id: String,
    override var productName: String,
    override var productPrice: Int,
    override var numberOfProductsPresentAtStore: Int,
    override var productMainCategory: String? = null,
    override var productSubCategory: String? = null,
    override var createdDate: Instant,
    override var status: DataStatus
): ProductListMinified
