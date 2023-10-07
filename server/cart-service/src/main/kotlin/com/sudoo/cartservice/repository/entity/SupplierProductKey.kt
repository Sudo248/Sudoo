package com.sudoo.cartservice.repository.entity

import lombok.*
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
class SupplierProductKey : Serializable {
    @Column(name = "product_id")
    var productId: String? = null

    @Column(name = "supplier_id")
    var supplierId: String? = null

    @Column(name = "cart_id")
    var cartId: String? = null

    constructor()
    constructor(productId: String?, supplierId: String?, cartId1: String?) {
        this.productId = productId
        this.supplierId = supplierId
        cartId = cartId1
    }
}

