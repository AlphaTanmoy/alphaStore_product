package com.alphaStore.product.service

import com.alphaStore.product.contract.ProductMerchantMappingRepo
import com.alphaStore.product.contract.ProductRepo
import com.alphaStore.product.entity.ProductMerchantMapping
import com.alphaStore.product.enums.DataStatus
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ProductMerchantSyncService(
    private val productRepo: ProductRepo,
    private val productMerchantMappingRepo: ProductMerchantMappingRepo
) {

    //@Scheduled(fixedRate = 3600000) // 1 hour
    @Scheduled(cron = "*/5 * * * * *") // 5 sec
    @Transactional
    fun updateMerchantProductMappingTable() {
        try {
            val productsGroupedByMerchant = productRepo.findAllByDataStatus(DataStatus.ACTIVE)
                .groupBy { it.merchantId }

            productsGroupedByMerchant.forEach { (merchantId, products) ->
                val productIds = products.joinToString(",") { it.id.toString() }
                val totalProductCount = products.size

                val newMapping = ProductMerchantMapping(
                    merchantId = merchantId,
                    productId = productIds,
                    totalProductCount = totalProductCount
                )
                productMerchantMappingRepo.save(newMapping)
            }
        } catch (e: DataIntegrityViolationException) {
            println("Duplicate entry for : ${e.message}. Skipping insertion")
        }
    }
}
