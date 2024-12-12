package com.alphaStore.product.enums

enum class ProductSellingStatus (val nameDescriptor: String)  {
    SOLD("Sold"),
    NOT_SOLD("Not Sold"),
    IN_STORE("In Store"),
    ORDERED("Ordered"),
    ON_DELIVERY("On Delivery"),
    RETURN("Return"),
    REFUND("Refund")
}