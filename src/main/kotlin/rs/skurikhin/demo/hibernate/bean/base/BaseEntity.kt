package rs.skurikhin.demo.hibernate.bean.base

import org.springframework.data.annotation.LastModifiedBy


abstract class BaseEntity {
    @LastModifiedBy
    private val modifiedBy: String? = null
}