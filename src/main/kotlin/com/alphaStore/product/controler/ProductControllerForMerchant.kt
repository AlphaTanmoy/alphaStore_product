package com.alphaStore.product.controler

import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.reqres.FilterOption
import com.alphaStore.product.service.ProductServiceForMerchant
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
@RequestMapping("/productForMerchant")
class ProductControllerForMerchant (
    private val productServiceForMerchant: ProductServiceForMerchant
){

    @GetMapping("/{merchantId}")
    fun getAllProductsByMerchantId(
        @PathVariable merchantId: String,
        @RequestParam("queryString") queryString: String? = null,
        @RequestParam("isActive") isActive: Boolean? = null,
        @RequestParam("offsetToken") offsetToken: String? = null,
        @RequestParam("giveCount") giveCount: Boolean = false,
        @RequestParam("productMainCategory") productMainCategory: String? = null,
        @RequestParam("productSubCategory") productSubCategory: String? = null,
        @RequestParam("considerMaxDateRange", defaultValue = "false") considerMaxDateRange: Boolean = false,
        @RequestParam("limit") limit: Int? = null,
        @RequestParam("giveData", defaultValue = "true") giveData: Boolean = true,
    ): PaginationResponse<ProductListMinifiedImpl> {
        val toRetFilterOption: ArrayList<FilterOption> = ArrayList()

        var queryStringFinal = "%"
        queryString?.let { obj ->
            toRetFilterOption.add(FilterOption("queryString", obj, obj))
            queryStringFinal = obj.split(',').joinToString("|") { "%${URLDecoder.decode(it, "UTF-8")}%" }
        }

        isActive?.let { isActiveTrue ->
            toRetFilterOption.add(
                FilterOption(
                    "isActive",
                    isActiveTrue.toString(),
                    isActiveTrue.toString(),
                )
            )
        }

        var productMainCategoryFinal = "%"
        productMainCategory?.let { obj ->
            toRetFilterOption.add(FilterOption("productMainCategory", obj, obj))
            productMainCategoryFinal =
                obj.split(',').joinToString("|") { "%${URLDecoder.decode(it, "UTF-8")}%" }
        }

        var productSubCategoryFinal = "%"
        productSubCategory?.let { obj ->
            toRetFilterOption.add(FilterOption("productSubCategory", obj, obj))
            productSubCategoryFinal =
                obj.split(',').joinToString("|") { "%${URLDecoder.decode(it, "UTF-8")}%" }
        }

        var pageSizeFinal = 5
        limit?.let {
            pageSizeFinal =
                if (it > 20)
                    20
                else
                    it
        }

        val resultFromDb = productServiceForMerchant.getProductsByMerchantId(
            merchantId = merchantId,
            queryString = queryStringFinal,
            considerMaxDateRange = considerMaxDateRange,
            toRetFilterOption = toRetFilterOption,
            giveCount = giveCount,
            limit = pageSizeFinal,
            isActive = isActive,
            giveData = giveData,
            productSubCategory = productSubCategoryFinal,
            productMainCategory = productMainCategoryFinal,
            offsetToken = offsetToken
        )
        return resultFromDb
    }


}