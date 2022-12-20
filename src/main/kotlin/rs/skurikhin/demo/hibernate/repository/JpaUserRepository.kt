package rs.skurikhin.demo.hibernate.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.skurikhin.demo.hibernate.bean.UserEntity
import java.util.*

@Repository
interface JpaUserRepository : JpaRepository<UserEntity?, Long?> {
    fun findByUserId(userId: Long): Optional<UserEntity>
    fun findByPhone(phone: Long): Optional<UserEntity>
}