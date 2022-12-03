package rs.skurikhin.demo.hibernate.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.skurikhin.demo.hibernate.bean.UserEntity
import rs.skurikhin.demo.hibernate.repository.JpaUserRepository
import rs.skurikhin.demo.hibernate.repository.UserRepository

@Service
class UserService(
    @Autowired private var userRepository: UserRepository,
    @Autowired private var jpaUserRepository: JpaUserRepository,
) {
    fun auth(phone: Long): UserEntity {
        val user = UserEntity().also {
            it.phone = phone
        }
//        userRepository.save(user)
        val res: UserEntity = jpaUserRepository.save(user)
        return res
    }
}