package com.sudoo.productservice.service.impl

import com.sudoo.domain.utils.Logger
import com.sudoo.productservice.repository.ProductRepository
import com.sudoo.productservice.service.ScheduleService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class ScheduleServiceImpl (
    private val productRepository: ProductRepository
) : ScheduleService {

    companion object {
        @OptIn(DelicateCoroutinesApi::class)
        private val scope = CoroutineScope(
            newSingleThreadContext("Midnight scheduler to reset price and discount for product thread") + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
                Logger.error(throwable)
            },
        )
    }

    @Value("\${timeZone}")
    private lateinit var timeZone: String

    // 1 second 0 minute 0 hour of every month every day
    @Scheduled(cron = "1 0 0 * * *", zone = "\${timeZone}")
    override fun runMidnightSchedule() {
        Logger.info("Midnight scheduler start")
        scope.launch {
            val currentDateTime = LocalDateTime.now().atZone(ZoneId.of(timeZone)).toLocalDateTime()
            val products = productRepository.findAll()
                .onEach {
                    if (it.endDateDiscount?.isBefore(currentDateTime) == true) {
                        it.discount = 0
                        it.price = it.listedPrice
                    }
                }
            productRepository.saveAll(products)
        }
    }
}