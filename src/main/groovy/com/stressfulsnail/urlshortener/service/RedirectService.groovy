package com.stressfulsnail.urlshortener.service

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.model.RedirectEntity
import com.stressfulsnail.urlshortener.repository.RedirectRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RedirectService {

    @Autowired
    private RedirectRepository redirectRepository

    RedirectDTO getRedirect(String urlKey) {
        RedirectEntity redirectEntity = redirectRepository.findByKey(urlKey)
        return new RedirectDTO().with {
            id = redirectEntity.id
            key = redirectEntity.key
            redirectUrl = redirectEntity.redirectUrl
            return it
        }
    }
}
