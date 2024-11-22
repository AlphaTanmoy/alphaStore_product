package com.alphaStore.product.contract

interface EncodingUtilContract {

    fun encode(toEncode: String): String

    fun decode(toDecode: String): String
}