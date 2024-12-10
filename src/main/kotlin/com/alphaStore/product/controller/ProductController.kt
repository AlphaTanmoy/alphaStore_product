package com.alphaStore.product.controller

import com.alphaStore.product.entity.Product
import com.alphaStore.product.enums.ProductSellingStatus
import com.alphaStore.product.model.CopyProduct
import com.alphaStore.product.model.minifiedImpl.ProductListMinifiedImpl
import com.alphaStore.product.service.ProductService
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.reqres.FilterOption
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.*
import java.net.URLDecoder

@RestController
@RequestMapping("/product")
class ProductController (
    private val productService: ProductService
) {

    /*@PostMapping("/create")
    fun createProduct(@RequestBody newProduct: Product): Product {
        return productService.createProduct(newProduct)
    }*/

    @PostMapping("/save")
    fun saveProduct(@RequestBody copyProduct: CopyProduct): Product {
        val productMapping = Product(
            productName = copyProduct.productName,
            productPrice = copyProduct.productPrice,
            productMainCategory = copyProduct.productMainCategory,
            productSubCategory = copyProduct.productSubCategory,
            merchantId = copyProduct.merchantId,
            batchId = copyProduct.batchId,
            uniqueProductId = copyProduct.uniqueProductId,
            companyName = copyProduct.companyName
        )

        productMapping.id = copyProduct.id
        productMapping.productSellingStatus = ProductSellingStatus.IN_STORE

        return productService.createProduct(productMapping)
    }
    

    @GetMapping("/getAll")
    fun getAllProducts(
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

        val resultFromDb = productService.getProducts(
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


    /*@PutMapping("/Product/{id}")
    fun updateProduct(@RequestBody newProduct: Product, @PathVariable id: Long): Product {
        return productService.findById(id)
            .map { Product ->

                productService.save(Product)
            }.orElseThrow { BadRequestException(errorMessage = "Not Found by $id") }
    }

    @DeleteMapping("/Product/{id}")
    fun deleteProduct(@PathVariable id: Long): String {
        if (!productService.existsById(id)) {
            throw BadRequestException(errorMessage = "Not Found by $id")
        }
        productService.deleteById(id)
        return "Product with id $id has been deleted successfully."
    }*/
}
