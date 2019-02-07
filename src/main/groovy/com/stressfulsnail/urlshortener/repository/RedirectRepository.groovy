package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.RedirectEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RedirectRepository extends JpaRepository<RedirectEntity, Long> {
    RedirectEntity findByKey(String key)
    Set<RedirectEntity> findAllByUserId(Long userId)
}
