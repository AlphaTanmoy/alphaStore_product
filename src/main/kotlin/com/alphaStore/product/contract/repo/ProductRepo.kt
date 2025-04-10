package com.alphaStore.product.contract.repo

import com.alphaStore.product.entity.Product
import com.alphaStore.product.model.minified.ProductListMinified
import com.alphaStore.product.enums.DataStatus
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Primary
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.ZonedDateTime

@Suppress("SqlDialectInspection", "SqlNoDataSourceInspection")
@Primary
interface ProductRepo : JpaRepository<Product, String> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(
        value = "DROP TABLE users CASCADE",
        nativeQuery = true
    )
    fun dropTable()

    @Query(
        value = "SELECT * FROM product_table p " +
                "WHERE p.id SIMILAR TO :queryString",
        nativeQuery = true
    )
    fun getAllProducts(
        @Param("queryString") queryString: String,
    ) : List<Product>

    fun findTop1ByOrderByCreatedDateAsc(): List<Product>

    @Query(
        value = "SELECT COUNT(*) FROM product_table p " +
                "WHERE (p.id SIMILAR TO :queryString OR p.product_name SIMILAR TO :queryString) " +
                "AND p.merchant_id SIMILAR TO :merchantId " +
                "AND p.product_main_category SIMILAR TO :productMainCategory " +
                "AND p.product_sub_category SIMILAR TO :productSubCategory " +
                "AND (CASE WHEN :isActiveRequired THEN p.data_status = :#{#dataStatus.name()} ELSE TRUE END)",
        nativeQuery = true
    )
    fun findCountWithOutOffsetIdAndDate(
        @Param("merchantId") merchantId: String,
        @Param("queryString") queryString: String,
        @Param("productMainCategory") productMainCategory: String,
        @Param("productSubCategory") productSubCategory: String,
        @Param("isActiveRequired") isActiveRequired: Boolean = false,
        @Param("dataStatus") dataStatus: DataStatus = DataStatus.ACTIVE
    ): Long

    @Query(
        value = "SELECT " +
                "CAST(p.product_name AS VARCHAR) as productName, " +
                "CAST(p.id AS VARCHAR) as id, " +
                "CAST(p.product_main_category AS VARCHAR) as productMainCategory, " +
                "CAST(p.product_sub_category AS VARCHAR) as productSubCategory, " +
                "p.product_in_store as productsInStore, " +
                "p.product_price as productPrice, " +
                "p.created_date as createdDate, " +
                "p.data_status as status " +
                "FROM product_table p " +
                "WHERE (p.id SIMILAR TO :queryString OR p.product_name SIMILAR TO :queryString) " +
                "AND p.merchant_id SIMILAR TO :merchantId " +
                "AND p.product_main_category SIMILAR TO :productMainCategory " +
                "AND p.product_sub_category SIMILAR TO :productSubCategory " +
                "AND (CASE WHEN :isActiveRequired THEN p.data_status = :#{#dataStatus.name()} ELSE TRUE END)",
        nativeQuery = true
    )
    fun findDataWithOutOffsetIdAndDate(
        @Param("merchantId") merchantId: String,
        @Param("queryString") queryString: String,
        @Param("productMainCategory") productMainCategory: String,
        @Param("productSubCategory") productSubCategory: String,
        @Param("isActiveRequired") isActiveRequired: Boolean = false,
        @Param("dataStatus") dataStatus: DataStatus = DataStatus.ACTIVE
    ): List<ProductListMinified>

    @Query(
        value = "SELECT " +
                "CAST(p.product_name AS VARCHAR) as productName, " +
                "CAST(p.id AS VARCHAR) as id, " +
                "CAST(p.product_main_category AS VARCHAR) as productMainCategory, " +
                "CAST(p.product_sub_category AS VARCHAR) as productSubCategory, " +
                "p.product_in_store as productsInStore, " +
                "p.product_price as productPrice, " +
                "p.created_date as createdDate, " +
                "p.data_status as status " +
                "FROM product_table p " +
                "WHERE p.created_date > :offsetDate " +
                "AND p.merchant_id SIMILAR TO :merchantId " +
                "AND (p.id SIMILAR TO :queryString OR p.product_name SIMILAR TO :queryString) " +
                "AND p.product_main_category SIMILAR TO :productMainCategory " +
                "AND p.product_sub_category SIMILAR TO :productSubCategory " +
                "AND (CASE WHEN :isActiveRequired THEN p.data_status = :#{#dataStatus.name()} ELSE TRUE END) " +
                "ORDER BY p.created_date ASC, p.id ASC " +
                "LIMIT :limit",
        nativeQuery = true
    )
    fun findDataWithOutOffsetId(
        @Param("merchantId") merchantId: String,
        @Param("queryString") queryString: String,
        @Param("offsetDate") offsetDate: ZonedDateTime,
        @Param("limit") limit: Int,
        @Param("productMainCategory") productMainCategory: String,
        @Param("productSubCategory") productSubCategory: String,
        @Param("isActiveRequired") isActiveRequired: Boolean = false,
        @Param("dataStatus") dataStatus: DataStatus = DataStatus.ACTIVE
    ): List<ProductListMinified>

    @Query(
        value = "SELECT " +
                "CAST(p.product_name AS VARCHAR) as productName, " +
                "CAST(p.id AS VARCHAR) as id, " +
                "CAST(p.product_main_category AS VARCHAR) as productMainCategory, " +
                "CAST(p.product_sub_category AS VARCHAR) as productSubCategory, " +
                "p.product_in_store as productsInStore, " +
                "p.product_price as productPrice, " +
                "p.created_date as createdDate, " +
                "p.data_status as status " +
                "FROM product_table p " +
                "WHERE p.id > :offsetId " +
                "AND p.merchant_id SIMILAR TO :merchantId " +
                "AND p.created_date = :offsetDate " +
                "AND (p.id SIMILAR TO :queryString OR p.product_name SIMILAR TO :queryString) " +
                "AND p.product_main_category SIMILAR TO :productMainCategory " +
                "AND p.product_sub_category SIMILAR TO :productSubCategory " +
                "AND (CASE WHEN :isActiveRequired THEN p.data_status = :#{#dataStatus.name()} ELSE TRUE END) " +
                "ORDER BY p.created_date ASC, p.id ASC " +
                "LIMIT :limit",
        nativeQuery = true
    )
    fun findDataWithOffsetId(
        @Param("merchantId") merchantId: String,
        @Param("queryString") queryString: String,
        @Param("offsetDate") offsetDate: ZonedDateTime,
        @Param("offsetId") offsetId: String,
        @Param("limit") limit: Int,
        @Param("productMainCategory") productMainCategory: String,
        @Param("productSubCategory") productSubCategory: String,
        @Param("isActiveRequired") isActiveRequired: Boolean = false,
        @Param("dataStatus") dataStatus: DataStatus = DataStatus.ACTIVE
    ): List<ProductListMinified>
}
