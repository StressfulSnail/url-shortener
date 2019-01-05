package com.stressfulsnail.urlshortener.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloWorldController {

    @RequestMapping('/')
    String helloWorld() {
        return 'Hello World!'
    }
}
