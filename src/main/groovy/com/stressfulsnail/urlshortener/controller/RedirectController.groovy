package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.service.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
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

    @GetMapping('/{key}')
    RedirectDTO getRedirect(@PathVariable String key) {
        RedirectDTO redirectDTO = redirectService.getRedirect(key)
        if (!redirectDTO) {
            throw new NotFoundException()
        }
        return redirectDTO
    }

    @PostMapping
    RedirectDTO createRedirect(@RequestBody @Validated RedirectDTO redirectDTO) {
        return redirectService.createRedirect(redirectDTO.redirectUrl)
    }

}
