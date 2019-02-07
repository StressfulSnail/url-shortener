package com.stressfulsnail.urlshortener.service

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.model.RedirectEntity
import com.stressfulsnail.urlshortener.repository.RedirectRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.*
import static org.mockito.BDDMockito.*

@RunWith(SpringRunner)
@WebMvcTest(value = RedirectService)
class RedirectServiceTests {

    @MockBean
    RedirectRepository redirectRepositoryMock

    @Autowired
    RedirectService redirectService

    @Test
    void getsRedirect() {
        def expectedRedirect = new RedirectEntity(id: 1, key: 'KEY', redirectUrl: 'website.com', userId: 2)
        given(redirectRepositoryMock.findByKey('KEY')).willReturn(expectedRedirect)

        RedirectDTO redirect = redirectService.getRedirect('KEY')

        assertThat(redirect.id).isEqualTo(1)
        assertThat(redirect.userId).isEqualTo(2)
        assertThat(redirect.key).isEqualTo('KEY')
        assertThat(redirect.redirectUrl).isEqualTo('website.com')
    }

    @Test
    void getsInvalidRedirect() {
        given(redirectRepositoryMock.findByKey('BAD_KEY')).willReturn(null)

        RedirectDTO redirect = redirectService.getRedirect('BAD_KEY')

        assertThat(redirect).isNull()
    }

    @Test
    void createsRedirect() {
        def redirectUrl = 'website.com'
        RedirectDTO redirectDTO = redirectService.createRedirect(redirectUrl, 2)

        verify(redirectRepositoryMock).save(any())
        assertThat(redirectDTO.id).isNull()
        assertThat(redirectDTO.userId).isEqualTo(2)
        assertThat(redirectDTO.redirectUrl).isEqualTo(redirectUrl)
        assertThat(redirectDTO.key).matches(/^temp[0-9]{4}$/)
    }

    @Test
    void findsValidRedirect() {
        def foundRedirect = new RedirectEntity()
        when(redirectRepositoryMock.findByKey('KEY')).thenReturn(foundRedirect)

        def success = redirectService.redirectExists('KEY')

        assertThat(success).isTrue()
    }

    @Test
    void findsInvalidRedirect() {
        when(redirectRepositoryMock.findByKey('BAD_KEY')).thenReturn(null)

        def success = redirectService.redirectExists('BAD_KEY')

        assertThat(success).isFalse()
    }

    @Test
    void deletesRedirect() {
        def foundRedirect = new RedirectEntity()
        when(redirectRepositoryMock.findByKey('KEY')).thenReturn(foundRedirect)

        redirectService.deleteRedirect('KEY')

        verify(redirectRepositoryMock).delete(foundRedirect)
    }
}
