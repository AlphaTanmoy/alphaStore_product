package com.alphaStore.product.model


import com.alphaStore.product.enums.DataStatus
import java.time.ZonedDateTime

data class Country(
    val id: String = "",
    val knownName: String = "",
    val officialName: String = "",
    val isdCode: String = "",
    val alpha2: String = "",
    val alpha3: String = "",
    val serviceable: Boolean,
    val mobileNumberValidationRegex: String = "",
    var lastUpdated: ZonedDateTime = ZonedDateTime.now(),
    var createdDate: ZonedDateTime = ZonedDateTime.now(),
    var dataStatus: DataStatus = DataStatus.ACTIVE
)
