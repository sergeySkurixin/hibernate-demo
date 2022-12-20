package rs.skurikhin.demo.hibernate.bean

import javax.persistence.*

@Entity
@Table(name = "article")
data class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var articleId: Long = 0L,
    var linkUrl: String? = null,
)
