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
            userId = redirectEntity.userId
            key = redirectEntity.key
            redirectUrl = redirectEntity.redirectUrl
            return it
        }
    }

    boolean redirectExists(String urlKey) {
        return redirectRepository.findByKey(urlKey)
    }

    RedirectDTO getRedirect(String urlKey) {
        RedirectEntity redirectEntity = redirectRepository.findByKey(urlKey)
        if (redirectEntity) {
            return poToDto(redirectEntity)
        }
        return null
    }

    RedirectDTO createRedirect(String redirectUrl, Long userId) {
        def random = new Random().nextInt(999) + 1000

        // TODO validate url format
        // TODO better random key

        RedirectEntity redirectEntity = new RedirectEntity(key: "temp${random}", redirectUrl: redirectUrl, userId: userId)
        redirectRepository.save(redirectEntity)

        return poToDto(redirectEntity)
    }

    void deleteRedirect(String urlKey) {
        def redirectEntity = redirectRepository.findByKey(urlKey)
        redirectRepository.delete(redirectEntity)
    }

    Set<RedirectDTO> getAllByUser(Long userId) {
        Set<RedirectEntity> redirects = redirectRepository.findAllByUserId(userId)
        return redirects.collect { poToDto(it) }
    }
}
