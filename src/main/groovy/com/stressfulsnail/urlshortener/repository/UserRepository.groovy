package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username)
}
