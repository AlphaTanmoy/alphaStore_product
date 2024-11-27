package com.alphaStore.product.feignClient

import com.alphaStore.product.reqres.MerchantResponse
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(name = "merchant-service", url = "http://localhost:8085/product")
interface MerchantClient {

    @GetMapping("/{merchantId}")
    fun getMerchantById(@PathVariable merchantId: String): MerchantResponse
}