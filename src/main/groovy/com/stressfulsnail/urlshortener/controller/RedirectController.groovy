package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.service.RedirectService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/api/redirect')
class RedirectController {

    @Autowired
    private RedirectService redirectService

    @GetMapping('/{key}')
    RedirectDTO getRedirect(@PathVariable String key) {
        return redirectService.getRedirect(key)
    }

}
