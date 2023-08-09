package br.com.leuras.app.controller

import br.com.leuras.app.extension.toConfirmationResponse
import br.com.leuras.app.model.OrderRequest
import br.com.leuras.core.exception.CustomerAccountNotFoundException
import br.com.leuras.core.exception.CustomerOrderNotFoundException
import br.com.leuras.core.usecase.OrderManagementUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController(
    private val useCase: OrderManagementUseCase) {

    companion object {
        private const val GENERIC_ERROR = "Failed to process the request due to: "
    }

    @PostMapping
    fun create(@RequestBody request: OrderRequest): ResponseEntity<Any> {
        return try {
            request.toTradingOrder().let {
                this.useCase.register(it)
                ResponseEntity(it.toConfirmationResponse(), HttpStatus.CREATED)
            }
        } catch (t: Throwable) {
            val message = when(t) {
                is CustomerAccountNotFoundException -> t.message
                else -> "$GENERIC_ERROR ${t.message}"
            }

            return ResponseEntity(mapOf("message" to message), HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/{id}")
    fun execute(@PathVariable("id") orderId: String): ResponseEntity<Any> {
        return try {
            val order = this.useCase.execute(orderId)
            ResponseEntity(order.toConfirmationResponse(), HttpStatus.ACCEPTED)
        } catch (t: Throwable) {
            val message = when(t) {
                is CustomerOrderNotFoundException -> t.message
                else -> "$GENERIC_ERROR ${t.message}"
            }

            return ResponseEntity(mapOf("message" to message), HttpStatus.NOT_FOUND)
        }
    }

    @DeleteMapping("/{id}/customer/{customer_id}")
    fun cancel(@PathVariable("id") orderId: String, @PathVariable("customer_id") customerId: String): ResponseEntity<Any> {
        return try {
            val order = this.useCase.cancel(customerId, orderId)
            ResponseEntity(order, HttpStatus.OK)
        } catch (t: Throwable) {
            val message = when(t) {
                is CustomerOrderNotFoundException -> t.message
                else -> "$GENERIC_ERROR ${t.message}"
            }

            return ResponseEntity(mapOf("message" to message), HttpStatus.NOT_FOUND)
        }
    }
}
