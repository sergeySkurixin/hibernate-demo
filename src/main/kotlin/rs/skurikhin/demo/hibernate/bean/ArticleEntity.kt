package rs.skurikhin.demo.hibernate.bean

import org.hibernate.envers.Audited
import javax.persistence.*

@Entity
@Table(name = "article")
@Audited
data class ArticleEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var articleId: Long = 0L,
    var linkUrl: String? = null,
)
