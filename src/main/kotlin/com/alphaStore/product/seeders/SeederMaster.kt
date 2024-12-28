package com.alphaStore.product.seeders

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class SeederMaster() : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String?) {
        print("Nothing to seed")
    }

}