package com.alphaStore.product.enums

enum class RefundStatus (val nameDescriptor: String)  {
    REFUND_ACCEPTED("Refund Accepted"),
    REFUND_REJECTED("Refund Rejected"),
    REFUND_PROCESSING("Refund Processing"),
    REFUND_DONE("Refund Done"),
    REFUND_INITIATED("Refund Initialized")
}