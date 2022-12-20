package rs.skurikhin.demo.hibernate.bean

import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(

    @Id
//    @GeneratedValue(generator = "system-uuid")
//    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Generated(GenerationTime.INSERT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var userId: Long = 0,

    //    @GeneratedValue(strategy = GenerationType.AUTO, generator = "user_id_seq")
//    var userId: Long = 0
    var phone: Long = 0,
    var email: String? = null,

    //    @ElementCollection
//    @CollectionTable
    @OneToMany(
        cascade = [CascadeType.ALL],
        fetch = FetchType.EAGER,
    )
//    @JoinColumn(name = "article_id")
    var favoriteArticles: MutableList<ArticleEntity> = mutableListOf()
)
