package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.service.RedirectService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.assertj.core.api.Assertions.*
import static org.mockito.BDDMockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner)
@WebMvcTest(value = RedirectController)
class RedirectControllerTests {

    @Autowired
    MockMvc mockMvc

    @MockBean
    RedirectService redirectServiceMock

    @Test
    void getValidRedirect() {
        def mockRedirect = new RedirectDTO(id: 1, key: 'KEY', redirectUrl: 'website.com')
        when(redirectServiceMock.getRedirect('KEY')).thenReturn(mockRedirect)

        mockMvc.perform(get('/api/redirect/KEY'))
            .andExpect(content().json('{ "id": 1, "key": "KEY", "redirectUrl": "website.com" }'))
    }

    @Test
    void getInvalidRedirect() {
        when(redirectServiceMock.getRedirect('BAD_KEY')).thenReturn(null)

        mockMvc.perform(get('/api/redirect/BAD_KEY'))
            .andExpect(status().isNotFound())
    }

    @Test
    void createValidRedirect() {
        def mockReturn = new RedirectDTO(id: 1, key: 'RAND', redirectUrl: 'url.com')
        when(redirectServiceMock.createRedirect('url.com')).thenReturn(mockReturn)

        mockMvc.perform(
            post('/api/redirect')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{ "redirectUrl": "url.com" }')
        )
            .andExpect(content().json('{ "id": 1, "key": "RAND", "redirectUrl": "url.com" }'))
    }

    @Test
    void createInvalidRedirect() {
        def mockReturn = new RedirectDTO(id: 1, key: 'RAND', redirectUrl: 'url.com')
        when(redirectServiceMock.createRedirect('url.com')).thenReturn(mockReturn)

        mockMvc.perform(
            post('/api/redirect')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}')
        )
            .andExpect(status().isBadRequest())
    }

    @Test
    void deleteValidRedirect() {
        when(redirectServiceMock.deleteRedirect('KEY')).thenReturn(true)

        mockMvc.perform(delete('/api/redirect/KEY'))
            .andExpect(status().is2xxSuccessful())
    }

    @Test
    void deleteInvalidRedirect() {
        when(redirectServiceMock.deleteRedirect('BAD_KEY')).thenReturn(false)

        mockMvc.perform(delete('/api/redirect/BAD_KEY'))
            .andExpect(status().isNotFound())
    }
}
