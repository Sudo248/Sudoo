package com.sudoo.productservice.model

import com.sudoo.domain.utils.IdentifyCreator
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("images")
data class Image(
    @Id
    @Column("image_id")
    val imageId: String,

    @Column("owner_id")
    val ownerId: String,

    @Column("url")
    val url: String,
) {
    companion object {
        fun from(ownerId: String, url: String): Image {
            return Image(
                imageId = IdentifyCreator.create(),
                ownerId = ownerId,
                url = url,
            )
        }
    }
}
