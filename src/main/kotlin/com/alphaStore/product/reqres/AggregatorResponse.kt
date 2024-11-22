package com.alphaStore.product.reqres

import java.util.*

data class AggregatorResponse<T>(
    var data: T,
    var databaseAccessLogId: String = UUID.randomUUID().toString()
)
