package com.alphaStore.product.model.minifiedImpl


import com.alphaStore.product.model.minified.FetchMostRecentMinified
import java.time.Instant

data class FetchMostRecentMinifiedImpl(
    override var id: String,
    override var createdDate: Instant
) : FetchMostRecentMinified