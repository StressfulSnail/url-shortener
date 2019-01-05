package com.stressfulsnail.urlshortener.modal

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = 'url')
@Inheritance(strategy = InheritanceType.JOINED)
class UrlEntity {
    @Id
    @Column
    Long id

    @Column(unique = true, nullable = false)
    String key

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<RequestEntity> requests = []
}
