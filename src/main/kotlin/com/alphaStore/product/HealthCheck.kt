package com.alphaStore.product

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/product")
class HealthCheck {

    @GetMapping("/healthCheck")
    fun healthCheck() : String {
        return "I Am Up"
    }

}