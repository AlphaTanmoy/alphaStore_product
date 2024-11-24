package com.alphaStore.product.reqres

import com.alphaStore.product.enums.ResponseType
import com.fasterxml.jackson.annotation.JsonFilter
import java.io.Serializable

data class GenericResponse(
    var code: Int? = null,
    var type: String? = null,
    var message: String = "",
    var responseType: ResponseType = ResponseType.SUCCESS,
) : Serializable

@JsonFilter("genericResponseFilter")
class GenericResponseMixIn