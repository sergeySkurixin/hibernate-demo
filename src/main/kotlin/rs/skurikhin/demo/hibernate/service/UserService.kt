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

    fun findUserByUserId(userId: Long): UserEntity? {
        return jpaUserRepository.findByUserId(userId)
    }

    fun findUserByPhone(phone: Long): UserEntity? {
        return jpaUserRepository.findByPhone(phone)
    }

    fun findAllUsers(): MutableList<UserEntity?> {
        return jpaUserRepository.findAll()
    }

//    fun addArticle(userId: String, article: ArticleEntity): UserEntity {
//        val user: UserEntity = findUser(userId).orElseThrow { RuntimeException("User not found") }!!
//        if (user.favoriteArticles == null) {
//            user.favoriteArticles = mutableListOf()
//        }
//        user.favoriteArticles!!.add(article)
//        val res = jpaUserRepository.save(user)
//        return res
//    }
}