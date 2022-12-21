package rs.skurikhin.demo.hibernate.bean

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
data class Country(
    @Id
    @GeneratedValue
    var cnrId: Int? = null,
    var countryName: String? = null,
)
