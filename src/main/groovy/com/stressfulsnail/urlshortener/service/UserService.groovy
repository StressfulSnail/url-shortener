package com.stressfulsnail.urlshortener.service

import com.stressfulsnail.urlshortener.dto.UserDTO
import com.stressfulsnail.urlshortener.model.RoleEntity
import com.stressfulsnail.urlshortener.model.UserEntity
import com.stressfulsnail.urlshortener.repository.RoleRepository
import com.stressfulsnail.urlshortener.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private UserRepository userRepository

    @Autowired
    private RoleRepository roleRepository

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder

    private static UserDTO userEntityToDTO(UserEntity userEntity) {
        Set<String> userRoles = userEntity.roles.collect { role -> role.role }
        return new UserDTO().with {
            id = userEntity.id
            username = userEntity.username
            roles = userRoles
            it
        }
    }

    boolean userExists(Long userId) {
        return userRepository.findById(userId)
    }

    Set<UserDTO> getAllUsers() {
        return userRepository.findAll().collect { userEntityToDTO(it) }
    }

    UserDTO getUser(Long userId) {
        def user = userRepository.findById(userId)
        if (user.isPresent()) {
            return userEntityToDTO(user.get())
        }
        return null
    }

    UserDTO createUser(UserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.roles.collect { roleRepository.findByRole(it) } // fix this - what happens when role not found?
        def user = new UserEntity().with {
            username = createUserDTO.username
            password = bCryptPasswordEncoder.encode(createUserDTO.password)
            it.roles = roles
            it
        }
        userRepository.save(user)
        return userEntityToDTO(user)
    }

    void deleteUser(Long userId) {
        def user = userRepository.findById(userId)
        userRepository.delete(user.get())
    }
}
