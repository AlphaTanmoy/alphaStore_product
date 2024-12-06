package com.alphaStore.product.entity

import com.alphaStore.product.entity.superentity.SuperEntityWithIdCreatedLastModifiedDataStatus
import com.alphaStore.product.enums.ProductMainCategory
import com.alphaStore.product.enums.ProductSubCategory
import com.fasterxml.jackson.annotation.JsonFilter
import jakarta.persistence.*
import jakarta.validation.constraints.*

@Entity
@Table(name = "product_table")
data class Product(
    @field:NotBlank(message = "Product name must not be blank")
    @Column(nullable = false)
    val productName: String = "",

    @field:NotNull(message = "Product price must not be null")
    @field:Min(value = 1, message = "Product price must be greater than or equal to 1")
    @Column(nullable = false)
    val productPrice: Long = 0,

    @field:NotNull(message = "Number of products at the store must not be null")
    @field:Min(value = 0, message = "Number of products at the store cannot be negative")
    @Column(nullable = false)
    val numberOfProductsPresentAtStore: Long = 0,

    @field:NotNull(message = "Number of products entered by merchant must not be null")
    @field:Min(value = 0, message = "Number of products entered by merchant cannot be negative")
    @Column(nullable = false)
    val numberOfProductsEnteredByMerchant: Long = 0,

    @field:NotNull(message = "Product main category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val productMainCategory: ProductMainCategory,

    @field:NotNull(message = "Product sub-category must not be null")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val productSubCategory: ProductSubCategory,

    var merchantId: String = "",

    var batchId: String = "",

    var uniqueId: String = ""
) : SuperEntityWithIdCreatedLastModifiedDataStatus()


@JsonFilter("ProductFilter")
class ProductMixIn