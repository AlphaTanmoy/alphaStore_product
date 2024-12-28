package com.alphaStore.product.service

import com.alphaStore.product.contract.repo.EncodingUtilContract
import com.alphaStore.product.contract.repo.EncryptionMasterContract
import com.alphaStore.product.entity.Product
import com.alphaStore.product.contract.aggregator.ProductRepoAggregator
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.enums.DateRangeType
import com.alphaStore.product.error.BadRequestException
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.reqres.FilterOption
import com.alphaStore.product.utils.ConverterStringToObjectList
import com.alphaStore.product.utils.DateUtil
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class ProductService(
    private val productRepoAggregator: ProductRepoAggregator,
    private val encodingUtilContract: EncodingUtilContract,
    private val encryptionMaster: EncryptionMasterContract,
    private val dateUtilContract: DateUtil
) {


    fun createProduct(product: Product): Product {
        try{
            val productToReturn = productRepoAggregator.save(product)
            return productToReturn
        }catch (ex: RestClientException) {
            throw IllegalStateException("Failed to validate Merchant ID: ${ex.message}")
        }
    }

    fun getProducts(
        merchantID: String,
        queryString: String,
        productSubCategory: String,
        productMainCategory: String,
        considerMaxDateRange: Boolean,
        dateRangeType: String? = null,
        toRetFilterOption: ArrayList<FilterOption>,
        offsetToken: String? = null,
        isActive: Boolean?,
        giveCount: Boolean,
        limit: Int,
        giveData: Boolean
    ): PaginationResponse<ProductListMinifiedImpl> {
        var offsetDateFinal: ZonedDateTime? = null
        var offsetId = ""

        offsetToken?.let {
            val decrypted = encryptionMaster.decrypt(
                encodingUtilContract.decode(
                    it
                ),
            )
            val splits = decrypted.split("::")
            val decryptedOffsetDate =
                dateUtilContract.getZonedDateTimeFromStringUsingIsoFormatServerTimeZone(splits[0])
            if (decryptedOffsetDate.isEmpty)
                throw BadRequestException("Please provide valid offset token")
            offsetDateFinal = decryptedOffsetDate.get()
            offsetId = splits[1]
        } ?: run {
            val firstCreated =
                productRepoAggregator.findTop1ByOrderByCreatedDateAsc()

            offsetDateFinal = if (firstCreated.data.isEmpty())
                null
            else {
                val instant = firstCreated.data[0]
                instant.let {
                    ZonedDateTime.ofInstant(it.createdDate.minusNanos(1000), ZoneId.of("UTC"))
                }
            }
        }

        offsetDateFinal ?: run {
            return PaginationResponse(
                arrayListOf(),
                filterUsed = toRetFilterOption,
            )
        }

        val toReturnAllProducts: ArrayList<ProductListMinifiedImpl> = ArrayList()
        var giveCountData = 0L

        if (giveCount) {
            val allUserCount =
                productRepoAggregator.findCountWithOutOffsetIdAndDate(
                    queryString = queryString,
                    productMainCategory = productMainCategory,
                    productSubCategory = productSubCategory,
                    isActive = isActive,
                    merchantId = merchantID
                )
            giveCountData = allUserCount.data

        }


        if (giveData) {
            if (considerMaxDateRange && dateRangeType != null && dateRangeType == DateRangeType.MAX.name) {
                val allProducts = productRepoAggregator.findDataWithOutOffsetIdAndDate(
                    queryString = queryString,
                    productMainCategory = productMainCategory,
                    productSubCategory = productSubCategory,
                    isActive = isActive,
                    merchantId = merchantID
                )
                toReturnAllProducts.addAll(allProducts.data)
            } else {
                if (offsetId.isBlank()) {
                    val productFirstPage =
                        offsetDateFinal.let {
                            productRepoAggregator.findDataWithOutOffsetId(
                                queryString = queryString,
                                productMainCategory = productMainCategory,
                                productSubCategory = productSubCategory,
                                isActive = isActive,
                                limit = limit,
                                offsetDate = offsetDateFinal!!,
                                merchantId = merchantID
                            )
                        }
                    if (productFirstPage.data.isEmpty()) {
                        return PaginationResponse(
                            arrayListOf(),
                            recordCount = giveCountData.toInt(),
                            filterUsed = toRetFilterOption
                        )
                    }
                    toReturnAllProducts.addAll(productFirstPage.data)
                } else {
                    val productNextPageWithSameData =
                        productRepoAggregator.findDataWithOffsetId(
                            queryString = queryString,
                            productMainCategory = productMainCategory,
                            productSubCategory = productSubCategory,
                            isActive = isActive,
                            limit = limit,
                            offsetDate = offsetDateFinal!!,
                            offsetId = offsetId,
                            merchantId = merchantID
                        )
                    val nextPageSize = limit - productNextPageWithSameData.data.size
                    val productNextPage = productRepoAggregator.findDataWithOutOffsetId(
                        queryString = queryString,
                        productMainCategory = productMainCategory,
                        productSubCategory = productSubCategory,
                        isActive = isActive,
                        limit = nextPageSize,
                        offsetDate = offsetDateFinal!!,
                        merchantId = merchantID
                    )
                    toReturnAllProducts.addAll(productNextPageWithSameData.data)
                    toReturnAllProducts.addAll(productNextPage.data)
                }
            }
            if (toReturnAllProducts.isEmpty()) {
                return PaginationResponse(
                    arrayListOf(),
                    recordCount = giveCountData.toInt(),
                    filterUsed = toRetFilterOption
                )
            } else {
                return PaginationResponse(
                    ConverterStringToObjectList.sanitizeForOutput(ArrayList(toReturnAllProducts)),
                    filterUsed = toRetFilterOption,
                    offsetToken = encodingUtilContract.encode(
                        encryptionMaster.encrypt(
                            "${
                                toReturnAllProducts.last().createdDate
                            }::${
                                toReturnAllProducts.last().id
                            }"
                        ),
                    ),
                    recordCount = giveCountData.toInt()
                )
            }
        } else {
            return PaginationResponse(
                filterUsed = toRetFilterOption,
                recordCount = giveCountData.toInt()
            )
        }
    }

}
