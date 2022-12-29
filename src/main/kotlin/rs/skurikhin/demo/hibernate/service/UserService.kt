package rs.skurikhin.demo.hibernate.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import rs.skurikhin.demo.hibernate.bean.*
import rs.skurikhin.demo.hibernate.repository.JpaCountryRepository
import rs.skurikhin.demo.hibernate.repository.JpaUserRepository
import rs.skurikhin.demo.hibernate.repository.UserRepository
import javax.transaction.Transactional

@Service
class UserService(
    @Autowired private var userRepository: UserRepository,
    @Autowired private var jpaUserRepository: JpaUserRepository,
    @Autowired private var countryRepository: JpaCountryRepository,
) {
    fun auth(phone: Long): UserEntity {
        val user = UserEntity().also {
            it.phone = phone
        }
        val res: UserEntity = jpaUserRepository.save(user)
        return res
    }

    @Transactional
    fun changeGender(userId: Long, gender: Gender): UserEntity {
        val user = jpaUserRepository.findByUserId(userId) ?: throw userNotFoundException()

        user.gender = gender
        return user
    }

    @Transactional
    fun changeCountryResidence(userId: Long, cnrId: Int?): UserEntity {
        val country = countryRepository.findById(cnrId).orElseThrow { countryNotExists(cnrId) }
        val user = jpaUserRepository.findByUserId(userId) ?: throw userNotFoundException()

        user.countryResidence = country
        log.info("Country residence changed for userId={}, country: {}", userId, country)
        return user
    }

    @Transactional
    fun addName(userId: Long, name: String): UserEntity {
        val user = jpaUserRepository.findByUserId(userId) ?: throw userNotFoundException()

//        user.names.add(UserName(firstName = name))
        user.addName(UserName(firstName = name))

        log.info("Changed name for userId={}, name: {}", userId, name)
        return user
    }

    fun findUserByUserId(userId: Long): UserEntity? {
        return jpaUserRepository.findByUserId(userId).also {
            log.info("Find user by userId={}, result: {}", userId, it)
        }
    }

    fun findUserByPhone(phone: Long): UserEntity? {
        return jpaUserRepository.findByPhone(phone).also {
            log.info("Find user by phone={}, result: {}", phone, it)
        }
    }

    fun findAllUsers(): MutableList<UserEntity?> {
        return jpaUserRepository.findAll()
    }

    @Transactional
    fun addArticle(userId: Long, url: String): UserEntity {
        log.info("Add article for userId={}, url='{}'", userId, url)
        val user: UserEntity = findUserByUserId(userId) ?: throw userNotFoundException()

        user.favoriteArticles.add(ArticleEntity(linkUrl = url))

//        return jpaUserRepository.save(user)
        return user
    }

    @Transactional
    fun addExternalLink(userId: Long, resourceName: String): UserEntity {
        val user: UserEntity = findUserByUserId(userId) ?: throw userNotFoundException()

        user.externalLinks.add(ExternalLinkEntity(resourceName = resourceName))

//        return jpaUserRepository.save(user)
        return user
    }

    private fun userNotFoundException() = RuntimeException("User not found")

    private fun countryNotExists(cnrId: Int?) = RuntimeException("Country not exists, cnrId=$cnrId")

    companion object {
        private val log = LoggerFactory.getLogger(UserService::class.java)
    }
}