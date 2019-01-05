package com.stressfulsnail.urlshortener.modal

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = 'redirect')
class RedirectEntity extends UrlEntity {
    @Column(name = 'redirect_url')
    String redirectUrl
}
