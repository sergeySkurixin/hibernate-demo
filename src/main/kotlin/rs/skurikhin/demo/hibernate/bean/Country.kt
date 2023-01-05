package rs.skurikhin.demo.hibernate.bean

import org.hibernate.envers.Audited
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table
@Audited
data class Country(
    @Id
    @GeneratedValue
    var cnrId: Int? = null,
    var countryName: String? = null,
)
