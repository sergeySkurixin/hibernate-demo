package rs.skurikhin.demo.hibernate

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import rs.skurikhin.demo.hibernate.service.UserService
import java.sql.ResultSet
import java.sql.SQLException
import javax.sql.DataSource
import kotlin.test.assertNull

@SpringBootTest(classes = [HibernateDemoMain::class])
@Testcontainers
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    @Throws(SQLException::class)
    fun testEmptySearch() {
//        printSqlTableStructure();
        val res = userService.findUserByUserId(123)
        assertNull(res)
    }

    @Test
    fun testSaveAndGet() {
        val phone = 12345L

        val auth = userService.auth(phone)
        val user = userService.findUserByPhone(phone)
        log.info("!!1 All users: {}", userService.findAllUsers())

        log.info("!!1 auth: {}", auth)
        log.info("!!1 user: {}", user)
        assertNotNull(user)
        assertEquals(phone, user!!.phone)
    }

    @Test
    fun testAddArticle() {
        val phone = 12345L
        val user = userService.auth(phone)
        val url1 = "abcd.ru"
        val url2 = "new.ru"

        userService.addArticle(user.userId, url1)
        userService.addArticle(user.userId, url2)

        val articles = userService.findUserByPhone(phone)!!.favoriteArticles
        log.info("favoriteArticles: $articles")
        assertEquals(2, articles.size)
        assertEquals(url1, articles[0].linkUrl)
        assertEquals(url2, articles[1].linkUrl)
    }

    @Throws(SQLException::class)
    private fun printSqlTableStructure(tableName: String = "users") {
        dataSource.connection.prepareStatement(
            """
                SELECT column_name, data_type
                FROM information_schema.columns
                WHERE
                 table_name ILIKE '%${tableName}%'
                ORDER BY table_name, column_name
                """
        )
            .use { ps ->
                val rs: ResultSet = ps.executeQuery()
                printResultSet(rs)
            }
    }

    @Throws(SQLException::class)
    private fun printResultSet(rs: ResultSet) {
        val builder = StringBuilder()
        val md = rs.metaData
        val count = md.columnCount
        for (i in 1..count) {
            builder.append(md.getColumnName(i)).append("\t|\t")
        }
        builder.append("\n")
        while (rs.next()) {
            for (i in 1..count) {
                builder.append(rs.getString(i)).append("\t|\t")
            }
            builder.append("\n")
        }
        log.info("Result set: {}", builder)
    }

    @Testcontainers
    companion object {
        private val log = LoggerFactory.getLogger(UserServiceTest::class.java)

        @JvmStatic
        @Container
        private val postgreSQLContainer: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:9.6.12")
            .withDatabaseName("integration-tests-db")
            .withUsername("sa")
            .withPassword("sa")

        @JvmStatic
        @DynamicPropertySource
        fun postgreSQLProperties(registry: DynamicPropertyRegistry) {
            registry.add("db.url") { postgreSQLContainer.jdbcUrl }
            registry.add("db.user") { postgreSQLContainer.username }
            registry.add("db.password") { postgreSQLContainer.password }
        }
    }
}