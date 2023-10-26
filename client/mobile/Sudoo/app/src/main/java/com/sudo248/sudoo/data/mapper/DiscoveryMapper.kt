package com.sudo248.sudoo.data.mapper

import com.sudo248.sudoo.data.dto.discovery.CategoryDto
import com.sudo248.sudoo.data.dto.discovery.CategoryInfoDto
import com.sudo248.sudoo.data.dto.discovery.CommentDto
import com.sudo248.sudoo.data.dto.discovery.CommentListDto
import com.sudo248.sudoo.data.dto.discovery.LocationDto
import com.sudo248.sudoo.data.dto.discovery.PaginationDto
import com.sudo248.sudoo.data.dto.discovery.ProductDto
import com.sudo248.sudoo.data.dto.discovery.ProductInfoDto
import com.sudo248.sudoo.data.dto.discovery.ProductListDto
import com.sudo248.sudoo.data.dto.discovery.SupplierDto
import com.sudo248.sudoo.data.dto.discovery.SupplierInfoDto
import com.sudo248.sudoo.data.dto.discovery.UpsertCommentDto
import com.sudo248.sudoo.data.dto.discovery.UserInfoDto
import com.sudo248.sudoo.domain.entity.discovery.Category
import com.sudo248.sudoo.domain.entity.discovery.CategoryInfo
import com.sudo248.sudoo.domain.entity.discovery.Comment
import com.sudo248.sudoo.domain.entity.discovery.CommentList
import com.sudo248.sudoo.domain.entity.discovery.Location
import com.sudo248.sudoo.domain.entity.discovery.Pagination
import com.sudo248.sudoo.domain.entity.discovery.Product
import com.sudo248.sudoo.domain.entity.discovery.ProductInfo
import com.sudo248.sudoo.domain.entity.discovery.ProductList
import com.sudo248.sudoo.domain.entity.discovery.Supplier
import com.sudo248.sudoo.domain.entity.discovery.SupplierInfo
import com.sudo248.sudoo.domain.entity.discovery.UpsertComment
import com.sudo248.sudoo.domain.entity.discovery.UserInfo

fun ProductDto.toProduct(): Product {
    return Product(
        productId = productId,
        name = name,
        description = description,
        sku = sku,
        price = price,
        listedPrice = listedPrice,
        amount = amount,
        soldAmount = soldAmount,
        rate = rate,
        discount = discount,
        startDateDiscount = startDateDiscount,
        endDateDiscount = endDateDiscount,
        saleable = saleable,
        weight = weight,
        height = height,
        length = length,
        width = width,
        images = images.map { it.url },
        supplier = supplier?.toSupplierInfo(),
        categories = categories?.map { it.toCategoryInfo() },
    )
}

fun ProductInfoDto.toProductInfo(): ProductInfo {
    return ProductInfo(
        productId = productId,
        name = name,
        sku = sku,
        price = price,
        listedPrice = listedPrice,
        amount = amount,
        rate = rate,
        discount = discount,
        startDateDiscount = startDateDiscount,
        endDateDiscount = endDateDiscount,
        saleable = saleable,
        images = images,
        brand = brand,
    )
}

fun ProductListDto.toProductList(): ProductList {
    return ProductList(
        products = products.map { it.toProductInfo() },
        pagination = pagination.toPagination()
    )
}

fun CategoryDto.toCategory(): Category {
    return Category(
        categoryId = categoryId,
        name = name,
        image = image,
    )
}

fun CategoryInfoDto.toCategoryInfo(): CategoryInfo {
    return CategoryInfo(
        categoryId, name, image
    )
}

fun SupplierDto.toSupplier(): Supplier {
    return Supplier(
        supplierId = supplierId,
        name = name,
        avatar = avatar,
        brand = brand,
        contactUrl = contactUrl,
        totalProducts = totalProducts,
        rate = rate,
        createAt = createAt,
        address = address.toAddress()
    )
}

fun SupplierInfoDto.toSupplierInfo(): SupplierInfo {
    return SupplierInfo(
        supplierId = supplierId,
        userId = userId,
        name = name,
        avatar = avatar,
        brand = brand,
        contactUrl = contactUrl,
        rate = rate,
    )
}

fun LocationDto.toLocation(): Location {
    return Location(longitude, latitude)
}

fun CommentDto.toComment(): Comment {
    return Comment(
        commentId = commentId,
        productId = productId,
        rate = rate,
        isLike = isLike,
        comment = comment,
        createAt = createAt,
        userInfo = userInfo.toUserInfo(),
        images = images,
    )
}

fun UserInfoDto.toUserInfo(): UserInfo {
    return UserInfo(
        userId,
        fullName,
        avatar,
    )
}

fun CommentListDto.toCommentList(): CommentList {
    return CommentList(
        comments = comments.map { it.toComment() },
        pagination = pagination.toPagination(),
    )
}

fun PaginationDto.toPagination(): Pagination {
    return Pagination(
        offset = offset,
        total = total
    )
}

fun UpsertComment.toUpsertCommentDto(): UpsertCommentDto {
    return UpsertCommentDto(
        productId, rate, isLike, comment, images
    )
}