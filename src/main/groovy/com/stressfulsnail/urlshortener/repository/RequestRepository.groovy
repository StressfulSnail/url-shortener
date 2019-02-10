package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.RequestEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository extends JpaRepository<RequestEntity, Long> {
}
