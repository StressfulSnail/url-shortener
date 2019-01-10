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

    private static RedirectDTO poToDto(RedirectEntity redirectEntity) {
        return new RedirectDTO().with {
            id = redirectEntity.id
            key = redirectEntity.key
            redirectUrl = redirectEntity.redirectUrl
            return it
        }
    }

    RedirectDTO getRedirect(String urlKey) {
        RedirectEntity redirectEntity = redirectRepository.findByKey(urlKey)
        if (redirectEntity) {
            return poToDto(redirectEntity)
        }
        return null
    }

    RedirectDTO createRedirect(String redirectUrl) {
        def random = new Random().nextInt(999) + 1000

        // TODO validate url format
        // TODO better random key

        RedirectEntity redirectEntity = new RedirectEntity(key: "temp${random}", redirectUrl: redirectUrl)
        redirectRepository.save(redirectEntity)

        return poToDto(redirectEntity)
    }

    boolean deleteRedirect(String urlKey) {
        RedirectEntity redirectEntity = redirectRepository.findByKey(urlKey)
        if (!redirectEntity) {
            return false
        }
        redirectRepository.delete(redirectEntity)
        return true
    }
}
