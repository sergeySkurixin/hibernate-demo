package rs.skurikhin.demo.hibernate.bean

import org.hibernate.envers.Audited
import javax.persistence.*

@Entity
@Table(name = "user_name")
@Audited
class UserName(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var nameId: Long = 0,
    var firstName: String? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    var user: UserEntity? = null,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserName

        return nameId == other.nameId
    }

    override fun hashCode(): Int {
        return  javaClass.hashCode()
    }

    override fun toString(): String {
        return "UserName(nameId=$nameId, firstName=$firstName, user=${user.let { "<non_null>" }})"
    }

}
