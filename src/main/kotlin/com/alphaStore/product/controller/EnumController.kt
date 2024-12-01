package com.alphaStore.product.controller

import com.alphaStore.product.enums.*
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.reqres.EnumOption
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class EnumController {

    @GetMapping("/enums")
    fun getAllEnums(): PaginationResponse<EnumOption> {
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

        ResponseType.entries.forEach{
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "ResponseType"
                )
            )
        }

        return PaginationResponse(data = toRet)
    }

    @GetMapping("/enums/fataStatus")
    fun getDataStatus(): PaginationResponse<EnumOption>{
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
        return PaginationResponse(data = toRet)
    }

    @GetMapping("/enums/pateRangeType")
    fun getDateRangeType(): PaginationResponse<EnumOption>{
        val toRet: ArrayList<EnumOption> = ArrayList()
        DateRangeType.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "DateRangeType"
                )
            )
        }
        return PaginationResponse(data = toRet)
    }

    @GetMapping("/enums/productMainCategory")
    fun getProductMainCategory(): PaginationResponse<EnumOption>{
        val toRet: ArrayList<EnumOption> = ArrayList()
        ProductMainCategory.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "ProductMainCategory"
                )
            )
        }
        return PaginationResponse(data = toRet)
    }

    @GetMapping("/enums/ProductSubCategory")
    fun getProductSubCategory(): PaginationResponse<EnumOption>{
        val toRet: ArrayList<EnumOption> = ArrayList()
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

    @GetMapping("/enums/responseType")
    fun getResponseType(): PaginationResponse<EnumOption>{
        val toRet: ArrayList<EnumOption> = ArrayList()
        ResponseType.entries.forEach {
            toRet.add(
                EnumOption(
                    name = it.name,
                    description = it.nameDescriptor,
                    category = "ResponseType"
                )
            )
        }
        return PaginationResponse(data = toRet)
    }

}