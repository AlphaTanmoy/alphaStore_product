package com.alphaStore.product.service

import com.alphaStore.product.contract.aggregator.ProductRepoAggregatorForMerchant
import com.alphaStore.product.contract.repo.EncodingUtilContract
import com.alphaStore.product.contract.repo.EncryptionMasterContract
import com.alphaStore.product.enums.DateRangeType
import com.alphaStore.product.error.BadRequestException
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.reqres.FilterOption
import com.alphaStore.product.utils.ConverterStringToObjectList
import com.alphaStore.product.utils.DateUtil
import org.springframework.stereotype.Component
import java.time.ZoneId
import java.time.ZonedDateTime

@Component
class ProductServiceForMerchant(
    private val productRepoAggregatorForMerchant: ProductRepoAggregatorForMerchant,
    private val encodingUtilContract: EncodingUtilContract,
    private val encryptionMaster: EncryptionMasterContract,
    private val dateUtilContract: DateUtil
) {


    fun getProductsByMerchantId(
        merchantId: String,
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
                productRepoAggregatorForMerchant.findTop1ByOrderByCreatedDateAscWithMerchantId(
                    merchantId = merchantId
                )

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
                productRepoAggregatorForMerchant.findCountWithOutOffsetIdAndDateWithMerchantId(
                    merchantId = merchantId,
                    queryString = queryString,
                    productMainCategory = productMainCategory,
                    productSubCategory = productSubCategory,
                    isActive = isActive,
                )
            giveCountData = allUserCount.data

        }


        if (giveData) {
            if (considerMaxDateRange && dateRangeType != null && dateRangeType == DateRangeType.MAX.name) {
                val allProducts = productRepoAggregatorForMerchant.findDataWithOutOffsetIdAndDateWithMerchantId(
                    merchantId = merchantId,
                    queryString = queryString,
                    productMainCategory = productMainCategory,
                    productSubCategory = productSubCategory,
                    isActive = isActive,
                )
                toReturnAllProducts.addAll(allProducts.data)
            } else {
                if (offsetId.isBlank()) {
                    val productFirstPage =
                        offsetDateFinal.let {
                            productRepoAggregatorForMerchant.findDataWithOutOffsetIdWithMerchantId(
                                merchantId = merchantId,
                                queryString = queryString,
                                productMainCategory = productMainCategory,
                                productSubCategory = productSubCategory,
                                isActive = isActive,
                                limit = limit,
                                offsetDate = offsetDateFinal!!
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
                        productRepoAggregatorForMerchant.findDataWithOffsetIdWithMerchantId(
                            merchantId = merchantId,
                            queryString = queryString,
                            productMainCategory = productMainCategory,
                            productSubCategory = productSubCategory,
                            isActive = isActive,
                            limit = limit,
                            offsetDate = offsetDateFinal!!,
                            offsetId = offsetId
                        )
                    val nextPageSize = limit - productNextPageWithSameData.data.size
                    val productNextPage = productRepoAggregatorForMerchant.findDataWithOutOffsetIdWithMerchantId(
                        merchantId = merchantId,
                        queryString = queryString,
                        productMainCategory = productMainCategory,
                        productSubCategory = productSubCategory,
                        isActive = isActive,
                        limit = nextPageSize,
                        offsetDate = offsetDateFinal!!
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