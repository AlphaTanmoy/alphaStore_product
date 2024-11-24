package com.alphaStore.product.error

class GenericException(
    var errorMessage: String = "",
    var code: Int = 0
) : RuntimeException()