package com.stressfulsnail.urlshortener.repository

import com.stressfulsnail.urlshortener.model.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByRole(String role)
}
