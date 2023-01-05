package rs.skurikhin.demo.hibernate.bean.request

import lombok.NoArgsConstructor

@NoArgsConstructor
data class CreateUserRequest(
//    @field:NotNull
    var phone: Long? = null,
)