package rs.skurikhin.demo.hibernate.bean

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class ArticleEntity {
    @Id
    var articleId: String = ""
    var linkUrl: String = ""
}