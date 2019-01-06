package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.RedirectEntity
import org.springframework.data.repository.CrudRepository

interface RedirectRepository extends CrudRepository<RedirectEntity, Long> {
    RedirectEntity findByKey(String key)
}
