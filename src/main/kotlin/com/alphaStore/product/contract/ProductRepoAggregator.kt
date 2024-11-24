package com.alphaStore.product.contract

import com.alphaStore.product.entity.Product
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.enums.DataStatus
import com.alphaStore.product.model.minifiedImpl.FetchMostRecentMinifiedImpl
import com.alphaStore.product.reqres.AggregatorListResponse
import com.alphaStore.product.reqres.AggregatorResponse

import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Suppress("UNREACHABLE_CODE")
@Component
class ProductRepoAggregator(
    private val productRepo: ProductRepo,
) {

    fun save(entity: Product): Product {
        return productRepo.save(entity)
    }

    fun findCountWithOutOffsetIdAndDate(
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?
    ): AggregatorResponse<Long> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepo.findCountWithOutOffsetIdAndDate(
                queryString = queryString,
                productMainCategory = productMainCategory,
                productSubCategory = productSubCategory,
                isActiveRequired = isActive!= null,
                dataStatus = if (isActive == true || isActive == null) DataStatus.ACTIVE else DataStatus.INACTIVE
            )
        return AggregatorResponse(data = resultFromDb, databaseAccessLogId)
    }

    fun findDataWithOutOffsetIdAndDate(
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?
    ): AggregatorListResponse<ProductListMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepo.findDataWithOutOffsetIdAndDate(
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

    fun findDataWithOutOffsetId(
        queryString: String,
        productMainCategory: String,
        productSubCategory: String,
        isActive: Boolean?,
        limit: Int,
        offsetDate: ZonedDateTime
    ): AggregatorListResponse<ProductListMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepo.findDataWithOutOffsetId(
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

    fun findDataWithOffsetId(
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
            productRepo.findDataWithOffsetId(
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

    fun findTop1ByOrderByCreatedDateAsc(
        skipCache: Boolean = false
    ): AggregatorListResponse<FetchMostRecentMinifiedImpl> {
        val databaseAccessLogId = UUID.randomUUID().toString()
        val resultFromDb =
            productRepo.findTop1ByOrderByCreatedDateAsc()
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