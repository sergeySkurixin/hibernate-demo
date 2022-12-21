package rs.skurikhin.demo.hibernate.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.skurikhin.demo.hibernate.bean.ArticleEntity
import rs.skurikhin.demo.hibernate.bean.ExternalLinkEntity
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

    //    @Transactional
    fun addArticle(userId: Long, url: String): UserEntity {
        val user: UserEntity = findUserByUserId(userId) ?: throw RuntimeException("User not found")

        user.favoriteArticles.add(ArticleEntity(linkUrl = url))

        return jpaUserRepository.save(user)
    }

    fun addExternalLink(userId: Long, resourceName: String): UserEntity {
        val user: UserEntity = findUserByUserId(userId) ?: throw RuntimeException("User not found")

        user.externalLinks.add(ExternalLinkEntity(resourceName = resourceName))

        return jpaUserRepository.save(user)
    }
}