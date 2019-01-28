package com.stressfulsnail.urlshortener.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = 'role')
class RoleEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'user_role_id')
    Long id

    @Column
    String role
}
