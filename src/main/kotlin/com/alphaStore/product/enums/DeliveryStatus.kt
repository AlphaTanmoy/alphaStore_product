package com.alphaStore.product.enums

enum class DeliveryStatus (val nameDescriptor: String)  {
    SHIPPING_STARTED("Shipping Started"),
    SHIPPING_DONE("Shipping Done"),
    SHIPPING_ON_PROCESS("Shipping On Process"),
    DELIVERY_COMPLETED("Delivery Completed"),
    DELIVERY_FAILED("Delivery Failed"),
    ASSIGNED_TO_DELIVERY_AGENT("Assigned to Delivery Agent"),

}