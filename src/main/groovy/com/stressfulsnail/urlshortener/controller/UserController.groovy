package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.UserDTO
import com.stressfulsnail.urlshortener.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/api/user')
class UserController {
    @Autowired
    private UserService userService

    @GetMapping
    Set<UserDTO> getUsers() {
        return userService.getAllUsers()
    }

    @GetMapping('/{userId}')
    UserDTO getUser(@PathVariable Long userId) {
        def user = userService.getUser(userId)
        if (!user) {
            throw new NotFoundException()
        }
        return user
    }

    @PostMapping
    UserDTO createUser(@RequestBody @Validated UserDTO createUserDTO) {
        return userService.createUser(createUserDTO)
    }

    @DeleteMapping('/{userId}')
    ResponseEntity deleteUser(@PathVariable Long userId) {
        if (!userService.userExists(userId)) {
            return ResponseEntity.status(404).body(null)
        }
        userService.deleteUser(userId)
        return ResponseEntity.status(204).body(null)
    }
}
