package br.com.sample.controller

import br.com.sample.service.UserService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun users() = this.userService.retrieveAll()

    @GetMapping("/{id}")
    fun user(@PathVariable id: String) = this.userService.retrieve(id)

    @GetMapping("/{id}/products")
    fun products(@PathVariable id: String) = this.userService.retrieveProducts()
}
