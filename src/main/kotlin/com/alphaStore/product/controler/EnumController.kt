package com.alphaStore.product.controler

import com.alphaStore.product.enums.DataStatus
import com.alphaStore.product.enums.DateRangeType
import com.alphaStore.product.enums.ProductMainCategory
import com.alphaStore.product.enums.ProductSubCategory
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.reqres.EnumOption
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class EnumController {

    @GetMapping("/enums")
    fun getDataStatus(): PaginationResponse<EnumOption> {
        val toRet: ArrayList<EnumOption> = ArrayList()
        DataStatus.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "DataStatus"
                )
            )
        }

        DateRangeType.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "DateRangeType"
                )
            )
        }

        ProductMainCategory.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "ProductMainCategory"
                )
            )
        }

        ProductSubCategory.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "ProductSubCategory"
                )
            )
        }

        return PaginationResponse(data = toRet)
    }

}