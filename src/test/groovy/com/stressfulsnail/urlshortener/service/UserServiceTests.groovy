package com.stressfulsnail.urlshortener.service

import com.stressfulsnail.urlshortener.dto.UserDTO
import com.stressfulsnail.urlshortener.model.RoleEntity
import com.stressfulsnail.urlshortener.model.UserEntity
import com.stressfulsnail.urlshortener.repository.RoleRepository
import com.stressfulsnail.urlshortener.repository.UserRepository
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.test.context.junit4.SpringRunner

import static org.assertj.core.api.Assertions.*
import static org.mockito.BDDMockito.*

@RunWith(SpringRunner)
@WebMvcTest(value = UserService)
class UserServiceTests {

    @MockBean
    UserRepository userRepositoryMock

    @MockBean
    RoleRepository roleRepositoryMock

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoderMock

    @Autowired
    UserService userService

    @Test
    void getsUser() {
        def userRoleEntity = new RoleEntity(role: 'USER')
        def expectedUser = new UserEntity(id: 1, username: 'test', password: 'HASH_PASS', roles: [ userRoleEntity ])
        given(userRepositoryMock.findById(1)).willReturn(Optional.of(expectedUser))

        def user = userService.getUser(1)

        assertThat(user.id).isEqualTo(1)
        assertThat(user.username).isEqualTo('test')
        assertThat(user.password).isNull()
        assertThat(user.roles).containsExactly('USER')
    }

    @Test
    void getsInvalidUser() {
        given(userRepositoryMock.findById(-1)).willReturn(Optional.empty())

        def user = userService.getUser(-1)

        assertThat(user).isNull()
    }

    @Test
    void createsUser() {
        ArgumentCaptor<UserEntity> argumentCaptor = ArgumentCaptor.forClass(UserEntity)

        def userDTO = new UserDTO(username: 'test', password: 'testpass', roles: [ 'USER' ])

        def userRoleEntity = new RoleEntity(role: 'USER')
        when(roleRepositoryMock.findByRole('USER')).thenReturn(userRoleEntity)
        when(bCryptPasswordEncoderMock.encode('testpass')).thenReturn('HASH_PASS')

        userService.createUser(userDTO)

        verify(userRepositoryMock).save(argumentCaptor.capture())
        def savedEntity = argumentCaptor.getValue()
        assertThat(savedEntity.id).isNull()
        assertThat(savedEntity.username).isEqualTo('test')
        assertThat(savedEntity.password).isEqualTo('HASH_PASS')
        assertThat(savedEntity.roles).containsExactly(userRoleEntity)
    }

    @Test
    void findsValidUser() {
        def foundUser = new UserEntity()
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(foundUser))

        def success = userService.userExists(1)

        assertThat(success).isTrue()
    }

    @Test
    void findsInvalidUser() {
        when(userRepositoryMock.findById(-1)).thenReturn(null)

        def success = userService.userExists(-1)

        assertThat(success).isFalse()
    }

    @Test
    void deletesUser() {
        def foundUser = new UserEntity()
        when(userRepositoryMock.findById(1)).thenReturn(Optional.of(foundUser))

        userService.deleteUser(1)

        verify(userRepositoryMock).delete(foundUser)
    }
}
