package rs.skurikhin.demo.hibernate.bean

import javax.persistence.*

@Entity
@Table(name = "external_links")
data class ExternalLinkEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var extLinkId: Long = 0,
    var resourceName: String? = null
)