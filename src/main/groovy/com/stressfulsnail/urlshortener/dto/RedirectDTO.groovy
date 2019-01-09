package com.stressfulsnail.urlshortener.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class RedirectDTO extends UrlDTO {
    @NotNull
    @Size(max = 2083)
    String redirectUrl
}
