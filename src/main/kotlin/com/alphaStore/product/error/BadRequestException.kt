package com.alphaStore.product.error

class BadRequestException(
    var errorMessage: String = "",
    var code: Int? = null,
    var type: String? = null
) : RuntimeException()