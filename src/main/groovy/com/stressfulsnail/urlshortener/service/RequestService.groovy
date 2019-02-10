package com.stressfulsnail.urlshortener.service

import com.stressfulsnail.urlshortener.dto.RequestDTO
import com.stressfulsnail.urlshortener.dto.UrlDTO
import com.stressfulsnail.urlshortener.model.RequestEntity
import com.stressfulsnail.urlshortener.model.UrlEntity
import com.stressfulsnail.urlshortener.repository.RequestRepository
import com.stressfulsnail.urlshortener.repository.UrlRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RequestService {
    @Autowired
    private RequestRepository requestRepository

    @Autowired
    private UrlRepository urlRepository

    void createRequest(RequestDTO requestDTO) {
        UrlEntity url = urlRepository.findByKey(requestDTO.url.key)
        def requestEntity = new RequestEntity().with {
            referer = requestDTO.referer
            userAgent = requestDTO.userAgent
            time = requestDTO.time
            it.url = url
            it
        }
        requestRepository.save(requestEntity)
    }
}
