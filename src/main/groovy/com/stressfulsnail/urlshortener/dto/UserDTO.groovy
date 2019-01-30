package com.stressfulsnail.urlshortener.dto

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class UserDTO {
    Long id

    @NotNull
    @Size(max = 255)
    String username

    @NotNull
    @Size(min = 8, max = 255)
    String password

    Set<String> roles = []
}
