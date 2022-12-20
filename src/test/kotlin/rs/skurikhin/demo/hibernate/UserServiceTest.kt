package rs.skurikhin.demo.hibernate

import org.junit.jupiter.api.Assertions
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

@SpringBootTest(classes = [HibernateDemoMain::class])
@Testcontainers
class UserServiceTest {
    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val dataSource: DataSource? = null

    @Test
    @Throws(SQLException::class)
    fun testEmptySearch() {
//        printSqlTableStructure();
        val res = userService!!.findUserByUserId(123)
        Assertions.assertNotNull(res)
    }

    @Test
    fun testSaveAndGet() {
        val phone = 12345
        val auth = userService!!.auth(phone.toLong())
        val user = userService.findUserByPhone(phone.toLong())
        log.info("!!1 All users: {}", userService.findAllUsers())
        Assertions.assertTrue(user.isPresent)
        log.info("!!1 auth: {}", auth)
        log.info("!!1 user: {}", user)
    }

    @Throws(SQLException::class)
    private fun printSqlTableStructure(tableName: String = "users") {
        dataSource!!.connection.prepareStatement(
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