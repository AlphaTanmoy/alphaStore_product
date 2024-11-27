package com.alphaStore.product.contract

import com.alphaStore.product.entity.ProductMerchantMapping
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductMerchantMappingRepo : JpaRepository<ProductMerchantMapping, String> {
    fun findByProductId(productId: String): Optional<ProductMerchantMapping>
}
