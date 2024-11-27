package com.alphaStore.product.contract.repo

interface EncodingUtilContract {

    fun encode(toEncode: String): String

    fun decode(toDecode: String): String
}