package com.alphaStore.product

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@EnableFeignClients(
	basePackages = [
		"com.alphaStore.product"
	])
@SpringBootApplication
@EnableScheduling
class ProductApplication

fun main(args: Array<String>) {
	runApplication<ProductApplication>(*args)
}
