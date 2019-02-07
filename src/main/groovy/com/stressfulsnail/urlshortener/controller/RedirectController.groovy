package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.service.RedirectService
import com.stressfulsnail.urlshortener.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/api/redirect')
class RedirectController {

    @Autowired
    private RedirectService redirectService

    @Autowired
    private UserService userService

    @GetMapping
    Set<RedirectDTO> getRedirects(Authentication auth) {
        Long userId = userService.getUserId(((User) auth.getPrincipal()).username)
        return redirectService.getAllByUser(userId)
    }

    @GetMapping('/{key}')
    RedirectDTO getRedirect(@PathVariable String key, Authentication auth) {
        Long userId = userService.getUserId(((User) auth.getPrincipal()).username)
        RedirectDTO redirectDTO = redirectService.getRedirect(key)
        if (!redirectDTO) {
            throw new NotFoundException()
        }
        if (redirectDTO.userId != userId) {
            throw new ForbiddenException()
        }
        return redirectDTO
    }

    @PostMapping
    RedirectDTO createRedirect(@RequestBody @Validated RedirectDTO redirectDTO, Authentication auth) {
        Long userId = userService.getUserId(((User) auth.getPrincipal()).username)
        return redirectService.createRedirect(redirectDTO.redirectUrl, userId)
    }

    @DeleteMapping('/{key}')
    ResponseEntity deleteRedirect(@PathVariable String key, Authentication auth) {
        Long userId = userService.getUserId(((User) auth.getPrincipal()).username)
        RedirectDTO redirectDTO = redirectService.getRedirect(key)
        if (!redirectDTO) {
            return ResponseEntity.status(404).body(null)
        }
        if (redirectDTO.userId != userId) {
            throw new ForbiddenException()
        }
        redirectService.deleteRedirect(key)
        return ResponseEntity.status(204).body(null)
    }

}
