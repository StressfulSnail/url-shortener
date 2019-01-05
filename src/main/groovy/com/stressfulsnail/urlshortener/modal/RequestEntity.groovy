package com.stressfulsnail.urlshortener.modal

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = 'request')
class RequestEntity {
    @Id
    @Column
    Long id

    @Column(name = 'ip_address')
    String ipAddress

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date time

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = 'url_id')
    UrlEntity url
}
