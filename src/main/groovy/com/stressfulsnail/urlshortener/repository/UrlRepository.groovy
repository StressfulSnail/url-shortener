package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.UrlEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    UrlEntity findByKey(String key)
}
