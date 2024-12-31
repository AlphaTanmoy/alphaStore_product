package com.alphaStore.product.service

import com.alphaStore.product.RestTemplateMaster
import com.alphaStore.product.error.BadRequestException
import com.alphaStore.product.model.PaginationResponse
import com.alphaStore.product.reqres.FilterOption
import org.springframework.http.HttpMethod
import com.alphaStore.product.model.Country
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.HttpServerErrorException
import org.springframework.web.util.UriComponentsBuilder

@Component
class CountryService(
    private val restTemplateMaster: RestTemplateMaster
) {


    fun getCountries(
        offsetToken: String?,
        toRetFilterOption: ArrayList<FilterOption>,
        giveCount: Boolean,
        queryStringFinal: String,
        serviceable: Boolean?,
        giveData: Boolean,
        considerMaxDateRange: Boolean,
        dateRangeType: String?,
        pageSizeFinal: Int,
    ): Any {
        try{
            val urlWithParams =
                UriComponentsBuilder.fromHttpUrl("http://localhost:8086/country/getAll")
                    .apply {
                        offsetToken?.let { queryParam("offsetToken", it) }
                        queryParam("queryString", queryStringFinal)
                        dateRangeType?.let { queryParam("dateRangeType", it) }
                        queryParam("considerMaxDateRange",considerMaxDateRange)
                        serviceable?.let { queryParam("serviceable", it) }
                        queryParam("giveCount",giveCount)
                        queryParam("giveData",giveData)
                        queryParam("limit", pageSizeFinal)
                    }
                    .toUriString()
            val response = restTemplateMaster.getRestTemplate().exchange(
                urlWithParams,
                HttpMethod.GET,
                null,
                PaginationResponse::class.java,
            )
            val paginationResponse = response.body
            val products = paginationResponse?.data ?: arrayListOf()
            val offsetTokenData = paginationResponse?.offsetToken
            val recordCount = paginationResponse?.recordCount ?: 0

            return PaginationResponse<Any>(
                data = ArrayList(products),
                offsetToken = offsetTokenData,
                recordCount = recordCount,
                filterUsed = toRetFilterOption
            )
        }catch (ex: HttpClientErrorException) {
            //logger.debug("HTTP Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request currently, Please try after some time later!")
        } catch (ex: HttpServerErrorException) {
            //logger.debug("Server Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request due to server error, Please try after some time later!")
        } catch (ex: Exception) {
            //logger.debug("Unexpected error occurred while fetching products : ${ex.message}")
            throw BadRequestException("Unexpected error occurred while fetching data products")
        }
    }

    fun getCountryByKnownName(
        knownName: String
    ): Country? {
        try{
            val urlWithParams =
                UriComponentsBuilder.fromHttpUrl(
                    "http://localhost:8086/country/knownName/$knownName"
                )
                    .toUriString()
            val response = restTemplateMaster.getRestTemplate().exchange(
                urlWithParams,
                HttpMethod.GET,
                null,
                Country::class.java,
            )
            val responseBody = response.body
            return responseBody

        } catch (ex: HttpClientErrorException) {
            //logger.debug("HTTP Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request currently, Please try after some time later!")
        } catch (ex: HttpServerErrorException) {
            //logger.debug("Server Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request due to server error, Please try after some time later!")
        } catch (ex: Exception) {
            //logger.debug("Unexpected error occurred while fetching products : ${ex.message}")
            throw BadRequestException("Unexpected error occurred while fetching data products")
        }
    }

    fun getCountryById(
        countryId: String,
    ): Country? {
        try{
            val urlWithParams =
                UriComponentsBuilder.fromHttpUrl(
                    "http://localhost:8085/country/id/$countryId"
                )
                    .toUriString()
            val response = restTemplateMaster.getRestTemplate().exchange(
                urlWithParams,
                HttpMethod.GET,
                null,
                Country::class.java,
            )
            val responseBody = response.body
            return responseBody

        } catch (ex: HttpClientErrorException) {
            //logger.debug("HTTP Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request currently, Please try after some time later!")
        } catch (ex: HttpServerErrorException) {
            //logger.debug("Server Error : {} - {}", ex.statusCode, ex.responseBodyAsString)
            throw BadRequestException("Unable to process your request due to server error, Please try after some time later!")
        } catch (ex: Exception) {
            //logger.debug("Unexpected error occurred while fetching products : ${ex.message}")
            throw BadRequestException("Unexpected error occurred while fetching data products")
        }
    }

    /*    fun getCountryByIsdCode(
            countryIsdCode: String,
            apisAccessLogId: String
        ): Country {

        }

        fun getCountryByAlpha2(
            alpha2Code: String,
            apisAccessLogId: String
        ): Country? {

        }*/
}