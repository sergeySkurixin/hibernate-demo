package rs.skurikhin.demo.hibernate.bean.request

import lombok.NoArgsConstructor
import rs.skurikhin.demo.hibernate.bean.Gender

@NoArgsConstructor
data class ChangeUserRequest(
    var email: String? = null,
    var gender: Gender? = null,
    var countryResidenceName: String? = null,
)