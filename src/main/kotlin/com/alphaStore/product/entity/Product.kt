package com.alphaStore.product.entity

import com.alphaStore.product.entity.superentity.SuperEntityWithIdCreatedLastModifiedDataStatus
import com.alphaStore.product.enums.ProductMainCategory
import com.alphaStore.product.enums.ProductSellingStatus
import com.alphaStore.product.enums.ProductSubCategory
import com.fasterxml.jackson.annotation.JsonFilter
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "product_table")
data class Product(
    @field:NotBlank(message = "Product name must not be blank")
    @Column(nullable = false)
    var productName: String = "",

    @field:NotNull(message = "Product price must not be null")
    @field:Min(value = 1, message = "Product price must be greater than or equal to 1")
    @Column(nullable = false)
    var productPrice: Long = 0,

    @field:NotNull(message = "Product main category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var productMainCategory: ProductMainCategory,

    @field:NotNull(message = "Product sub-category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var productSubCategory: ProductSubCategory,

    @Column(nullable = false)
    var merchantId: String = "",

    @Column(nullable = false)
    var batchGroup: String = "",

    @Column(nullable = false)
    var productInStore: Long = 0,

    @Column(nullable = false)
    var companyName: String = "",

    @Column(nullable = false)
    var productSellingStatus: ProductSellingStatus = ProductSellingStatus.IN_STORE
) : SuperEntityWithIdCreatedLastModifiedDataStatus()


@JsonFilter("ProductFilter")
class ProductMixIn