package com.sudoo.productservice.service.impl

import com.sudoo.domain.utils.Logger
import com.sudoo.productservice.model.UserProduct
import com.sudoo.productservice.repository.ProductRepository
import com.sudoo.productservice.repository.SupplierRepository
import com.sudoo.productservice.service.CoreService
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class CoreServiceImpl(
    private val productRepository: ProductRepository,
    private val supplierRepository: SupplierRepository,
) : CoreService {

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val scope = CoroutineScope(
            newSingleThreadContext("Calculate rating for product thread") + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                Logger.error(throwable)
            },
        )
    }

    private val channel = Channel<UserProduct>(Channel.UNLIMITED)

    init {
        Logger.info("CoreService: Start listen comment")
        scope.launch {
            while (true) {
                val comment = channel.receive()
                Logger.info("Receive comment with rate ${comment.rate} at ${comment.createAt}")
                val product = productRepository.findById(comment.productId)
                    ?: throw Exception("Not found product ${comment.productId}")
                val currentProductRate = product.rate
                val currentProductTotalRate = product.totalRateAmount * product.rate
                val productRate = (currentProductTotalRate + comment.rate) / (product.totalRateAmount + 1)
                product.rate = productRate
                product.totalRateAmount += 1
                productRepository.save(product)

                val supplier = supplierRepository.findById(product.supplierId)
                    ?: throw Exception("Not found supplier ${product.supplierId}")
                val totalProduct = productRepository.countBySupplierId(supplier.supplierId)
                val currentSupplierTotalRate = supplier.rate * totalProduct
                val supplierRate = (currentSupplierTotalRate + (productRate - currentProductRate)) / totalProduct
                supplier.rate = supplierRate
                supplierRepository.save(supplier)
            }
        }
    }

    override suspend fun upsertComment(comment: UserProduct) {
        channel.send(comment)
    }
}