package com.alphaStore.product.model.minifiedImpl

import com.alphaStore.product.model.minified.ProductIDAndProductNameResponse

data class ProductIDAndProductNameResponseImpl (
    override var productName: String = "",
    override var productId: String = "",
    override var merchantId: String = ""
): ProductIDAndProductNameResponse