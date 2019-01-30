package com.stressfulsnail.urlshortener.controller

import com.stressfulsnail.urlshortener.dto.RedirectDTO
import com.stressfulsnail.urlshortener.dto.UserDTO
import com.stressfulsnail.urlshortener.service.UserService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc

import static org.assertj.core.api.Assertions.*
import static org.mockito.BDDMockito.*
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@RunWith(SpringRunner)
@WebMvcTest(value = UserController)
@WithMockUser(authorities = [ 'ADMIN' ])
class UserControllerTests {

    @Autowired
    MockMvc mockMvc

    @MockBean
    UserService userServiceMock

    @Test
    void getAllUsers() {
        Set<UserDTO> mockUsers = [
            new UserDTO(id: 1, username: 'test', roles: [ 'USER' ]),
            new UserDTO(id: 2, username: 'test2', roles: [ 'ADMIN', 'USER' ])
        ]
        when(userServiceMock.getAllUsers()).thenReturn(mockUsers)

        mockMvc.perform(get('/api/user'))
            .andExpect(content().json('[ { "id": 1, "username": "test", "roles": [ "USER" ] }, { "id": 2, "username": "test2", "roles": [ "ADMIN", "USER" ] } ]'))
    }

    @Test
    void getValidUser() {
        def mockUser = new UserDTO(id: 1, username: 'test', roles: [ 'USER' ])
        when(userServiceMock.getUser(1)).thenReturn(mockUser)

        mockMvc.perform(get('/api/user/1'))
            .andExpect(content().json('{ "id": 1, "username": "test", "roles": [ "USER" ] }'))
    }

    @Test
    void getInvalidUser() {
        when(userServiceMock.getUser(-1)).thenReturn(null)

        mockMvc.perform(get('/api/user/-1'))
            .andExpect(status().isNotFound())
    }

    @Test
    void createValidUser() {
        ArgumentCaptor<UserDTO> argumentCaptor = ArgumentCaptor.forClass(UserDTO)

        def mockReturn = new UserDTO(id: 1, username: 'test', roles: [ 'USER' ])
        when(userServiceMock.createUser(any(UserDTO))).thenReturn(mockReturn)

        mockMvc.perform(
            post('/api/user')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{ "username": "test", "password": "testpass", "roles": [ "USER" ] }')
        )
            .andExpect(content().json('{ "id": 1, "username": "test", "roles": [ "USER" ] }'))

        verify(userServiceMock).createUser(argumentCaptor.capture())
        def createdDTO = argumentCaptor.getValue()
        assertThat(createdDTO.username).isEqualTo('test')
        assertThat(createdDTO.password).isEqualTo('testpass')
        assertThat(createdDTO.roles.size()).isEqualTo(1)
        assertThat(createdDTO.roles.contains('USER')).isTrue()
    }

    @Test
    void createInvalidUser() {
        mockMvc.perform(
            post('/api/user')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{}')
        )
            .andExpect(status().isBadRequest())

        mockMvc.perform(
            post('/api/user')
                .contentType(MediaType.APPLICATION_JSON)
                .content('{ "username": "test", "password": "short", "roles": [ "USER" ] }')
        )
            .andExpect(status().isBadRequest())
    }

    @Test
    void deleteValidUser() {
        when(userServiceMock.userExists(1)).thenReturn(true)

        mockMvc.perform(delete('/api/user/1'))
            .andExpect(status().is2xxSuccessful())
    }

    @Test
    void deleteInvalidUser() {
        when(userServiceMock.userExists(-1)).thenReturn(false)

        mockMvc.perform(delete('/api/user/11'))
            .andExpect(status().isNotFound())
    }

    @Test
    @WithMockUser(authorities = [])
    void nonUserCannotAccess() {
        mockMvc.perform(get('/api/user'))
            .andExpect(status().isForbidden())

        mockMvc.perform(get('/api/user/1'))
            .andExpect(status().isForbidden())

        mockMvc.perform(post('/api/user/1'))
            .andExpect(status().isForbidden())

        mockMvc.perform(delete('/api/user/1'))
            .andExpect(status().isForbidden())
    }

    @Test
    @WithMockUser(authorities = [ 'USER' ])
    void nonAdminUserCannotAccess() {
        mockMvc.perform(get('/api/user'))
            .andExpect(status().isForbidden())

        mockMvc.perform(get('/api/user/1'))
            .andExpect(status().isForbidden())

        mockMvc.perform(post('/api/user/1'))
            .andExpect(status().isForbidden())

        mockMvc.perform(delete('/api/user/1'))
            .andExpect(status().isForbidden())
    }
}
