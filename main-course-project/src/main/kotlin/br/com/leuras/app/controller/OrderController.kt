package br.com.leuras.app.controller

import br.com.leuras.app.extension.toConfirmationResponse
import br.com.leuras.app.model.OrderRequest
import br.com.leuras.core.exception.CustomerAccountNotFoundException
import br.com.leuras.core.usecase.OrderManagementUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController(
    private val useCase: OrderManagementUseCase) {

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
                else -> ("Unknown error")
            }

            return ResponseEntity(mapOf("message" to message), HttpStatus.BAD_REQUEST)
        }
    }
}
