package com.alphaStore.product.entity

import com.fasterxml.jackson.annotation.JsonFilter
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "product_merchant_mapping")
data class ProductMerchantMapping(
    @Column(nullable = false, unique = true)
    var productId: String,

    @Column(nullable = false)
    val merchantId: String,

    @Column(nullable = false)
    var totalProductCount: Int = 0
) : SuperEntityWithIdCreatedLastModifiedDataStatus()


@JsonFilter("ProductMerchantMappingFilter")
class ProductMerchantMappingMixIn