package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.dto.RequestDTO
import com.stressfulsnail.urlshortener.service.RedirectService
import com.stressfulsnail.urlshortener.service.RequestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

import javax.servlet.http.HttpServletRequest

@RestController
class UrlController {

    @Autowired
    RedirectService redirectService

    @Autowired
    RequestService requestService

    @GetMapping('/r/{urlKey}')
    RedirectView getRedirect(@PathVariable String urlKey, HttpServletRequest request) {
        RedirectDTO redirect = redirectService.getRedirect(urlKey)
        if (!redirect) {
            throw new NotFoundException()
        }

        def requestData = new RequestDTO().with {
            referer = request.getHeader('Referer')
            userAgent = request.getHeader('User-Agent')
            time = new Date()
            it.url = redirect
            it
        }

        requestService.createRequest(requestData)
        return new RedirectView(redirect.redirectUrl)
    }
}
