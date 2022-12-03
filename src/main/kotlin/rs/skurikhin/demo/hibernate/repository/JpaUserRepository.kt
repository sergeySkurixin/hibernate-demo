package rs.skurikhin.demo.hibernate.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rs.skurikhin.demo.hibernate.bean.UserEntity

@Repository
interface JpaUserRepository : JpaRepository<UserEntity?, String?>