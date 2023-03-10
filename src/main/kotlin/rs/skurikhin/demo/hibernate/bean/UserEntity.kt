package rs.skurikhin.demo.hibernate.bean

import org.hibernate.annotations.LazyCollection
import org.hibernate.annotations.LazyCollectionOption
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited
import rs.skurikhin.demo.hibernate.bean.base.BaseEntity
import javax.persistence.*

@Entity
@Table(name = "users")
@Audited
data class UserEntity(

    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0,
    @Column(unique = true)
    var phone: Long = 0,
    var email: String? = null,

    @Enumerated(value = EnumType.ORDINAL)
    var gender: Gender? = null,

    @OneToOne
    var countryResidence: Country? = null,

    /** example of OneToMany. It creates table with mapping between {@link ArticleEntity} and {@link UserEntity} */
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var favoriteArticles: MutableList<ArticleEntity> = mutableListOf(),

    /** example of OneToMany without 3rd(auxiliary) table(because of {@link JoinColumn} */
    @OneToMany(cascade = [CascadeType.ALL])
    @LazyCollection(LazyCollectionOption.FALSE) // same as fetch = FetchType.EAGER, because hibernate not allow few collections with FetchType.EAGER
    @JoinColumn(name = "user_id")
    @NotAudited
    var externalLinks: MutableList<ExternalLinkEntity> = mutableListOf(),

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL]
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    var names: MutableList<UserName> = mutableListOf(),
) : BaseEntity() {
    fun addName(name: UserName) {
        names.add(name)
        name.user = this
    }
}
