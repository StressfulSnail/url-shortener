package com.stressfulsnail.urlshortener.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'redirect')
class RedirectEntity extends UrlEntity {
    @Column(name = 'redirect_url')
    String redirectUrl
}
