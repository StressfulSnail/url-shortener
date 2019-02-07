package com.stressfulsnail.urlshortener.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'url')
@Inheritance(strategy = InheritanceType.JOINED)
class UrlEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = 'url_id')
    Long id

    @Column(name = 'url_key', unique = true, nullable = false)
    String key

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = 'url')
    Set<RequestEntity> requests = []

    @Column(name = 'user_id', nullable = false)
    Long userId
}
