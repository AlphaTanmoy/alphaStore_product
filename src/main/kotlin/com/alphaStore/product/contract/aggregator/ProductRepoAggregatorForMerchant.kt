package com.alphaStore.product.contract.aggregator

import com.alphaStore.product.contract.repo.ProductRepoForMerchant
import com.alphaStore.product.entity.Product
import com.alphaStore.product.enums.DataStatus
import com.alphaStore.product.model.minifiedImpl.FetchMostRecentMinifiedImpl
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.reqres.AggregatorListResponse
import com.alphaStore.product.reqres.AggregatorResponse
import java.time.ZonedDateTime
import java.util.*

class ProductRepoAggregatorForMerchant(
    private val productRepoForMerchant: ProductRepoForMerchant
) {


    fun save(entity: Product): Product {
        return productRepoForMerchant.save(entity)
    }

    fun findCountWithOutOffsetIdAndDateWithMerchantId(
        merchantId: String,
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?
    ): AggregatorResponse<Long> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepoForMerchant.findCountWithOutOffsetIdAndDateWithMerchantId(
                merchantId = merchantId,
                queryString = queryString,
                productMainCategory = productMainCategory,
                productSubCategory = productSubCategory,
                isActiveRequired = isActive!= null,
                dataStatus = if (isActive == true || isActive == null) DataStatus.ACTIVE else DataStatus.INACTIVE
            )
        return AggregatorResponse(data = resultFromDb, databaseAccessLogId)
    }

    fun findDataWithOutOffsetIdAndDateWithMerchantId(
        merchantId: String,
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?
    ): AggregatorListResponse<ProductListMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepoForMerchant.findDataWithOutOffsetIdAndDateWithMerchantId(
                merchantId = merchantId,
                queryString = queryString,
                productMainCategory = productMainCategory,
                productSubCategory = productSubCategory,
                isActiveRequired = isActive!= null,
                dataStatus = if (isActive == true || isActive == null) DataStatus.ACTIVE else DataStatus.INACTIVE
            ).map{ toMap->
                ProductListMinifiedImpl(
                    id = toMap.id,
                    productName = toMap.productName,
                    productPrice = toMap.productPrice,
                    numberOfProductsPresentAtStore = toMap.numberOfProductsPresentAtStore,
                    productMainCategory = toMap.productMainCategory,
                    productSubCategory = toMap.productSubCategory,
                    createdDate = toMap.createdDate,
                    status = toMap.status,
                    merchantId = toMap.merchantId
                )
            }.toCollection(ArrayList())
        return AggregatorListResponse(data = resultFromDb, databaseAccessLogId)
    }

    fun findDataWithOutOffsetIdWithMerchantId(
        merchantId: String,
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?,
        limit: Int,
        offsetDate: ZonedDateTime
    ): AggregatorListResponse<ProductListMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepoForMerchant.findDataWithOutOffsetIdWithMerchantId(
                merchantId = merchantId,
                queryString = queryString,
                productMainCategory = productMainCategory,
                productSubCategory = productSubCategory,
                isActiveRequired = isActive != null,
                dataStatus = if (isActive == true || isActive == null) DataStatus.ACTIVE else DataStatus.INACTIVE,
                limit = limit,
                offsetDate = offsetDate
            ).map{ toMap->
                ProductListMinifiedImpl(
                    id = toMap.id,
                    productName = toMap.productName,
                    productPrice = toMap.productPrice,
                    numberOfProductsPresentAtStore = toMap.numberOfProductsPresentAtStore,
                    productMainCategory = toMap.productMainCategory,
                    productSubCategory = toMap.productSubCategory,
                    createdDate = toMap.createdDate,
                    status = toMap.status,
                    merchantId = toMap.merchantId
                )
            }.toCollection(ArrayList())
        return AggregatorListResponse(data = resultFromDb, databaseAccessLogId)
    }

    fun findDataWithOffsetIdWithMerchantId(
        merchantId: String,
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?,
        limit: Int,
        offsetDate: ZonedDateTime,
        offsetId: String
    ): AggregatorListResponse<ProductListMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepoForMerchant.findDataWithOffsetIdWithMerchantId(
                merchantId = merchantId,
                queryString = queryString,
                productMainCategory = productMainCategory,
                productSubCategory = productSubCategory,
                isActiveRequired = isActive != null,
                dataStatus = if (isActive == true || isActive == null) DataStatus.ACTIVE else DataStatus.INACTIVE,
                offsetDate = offsetDate,
                limit = limit,
                offsetId = offsetId
            ).map{ toMap->
                ProductListMinifiedImpl(
                    id = toMap.id,
                    productName = toMap.productName,
                    productPrice = toMap.productPrice,
                    numberOfProductsPresentAtStore = toMap.numberOfProductsPresentAtStore,
                    productMainCategory = toMap.productMainCategory,
                    productSubCategory = toMap.productSubCategory,
                    createdDate = toMap.createdDate,
                    status = toMap.status,
                    merchantId = toMap.merchantId
                )
            }.toCollection(ArrayList())
        return AggregatorListResponse(data = resultFromDb, databaseAccessLogId)
    }

    fun findTop1ByOrderByCreatedDateAscWithMerchantId(
        merchantId: String,
    ): AggregatorListResponse<FetchMostRecentMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepoForMerchant.findTop1ByOrderByCreatedDateAscWithMerchantId(
                merchantId = merchantId
            )
                .map { toMap ->
                    FetchMostRecentMinifiedImpl(
                        id = toMap.id,
                        createdDate = toMap.createdDate.toInstant()
                    )
                }
                .toCollection(ArrayList())
        return AggregatorListResponse(data = ArrayList(resultFromDb), databaseAccessLogId)
    }

}