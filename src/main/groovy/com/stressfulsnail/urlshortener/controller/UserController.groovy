package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.UserDTO
import com.stressfulsnail.urlshortener.service.UserService
import org.springframework.beans.factory.annotation.Autowired
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
        return userService.getUser(userId)
    }

    @PostMapping
    UserDTO createUser(@RequestBody @Validated UserDTO createUserDTO) {
        return userService.createUser(createUserDTO)
    }

    @DeleteMapping('/{userId}')
    void deleteUser(@PathVariable Long userId) {

    }
}
