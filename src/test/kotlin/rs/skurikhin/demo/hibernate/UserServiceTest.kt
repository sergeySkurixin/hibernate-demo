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
import org.testcontainers.shaded.org.apache.commons.lang3.RandomUtils
import rs.skurikhin.demo.hibernate.bean.Country
import rs.skurikhin.demo.hibernate.bean.Gender
import rs.skurikhin.demo.hibernate.repository.JpaCountryRepository
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
    private lateinit var countryRepository: JpaCountryRepository

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
        val phone = RandomUtils.nextLong()

        val auth = userService.auth(phone)
        val user = userService.findUserByPhone(phone)
        log.info("!!1 All users: {}", userService.findAllUsers())

        log.info("!!1 auth: {}", auth)
        log.info("!!1 user: {}", user)
        assertNotNull(user)
        assertEquals(phone, user!!.phone)
        assertEquals(0, user.version)
    }

    @Test
    fun testAddArticle() {
        val phone = RandomUtils.nextLong()
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

    @Test
    fun testAddExternalLinks() {
        val phone = RandomUtils.nextLong()
        val user = userService.auth(phone)
        val resource1 = "vk.ru"
        val resource2 = "google.com"

        userService.addExternalLink(user.userId, resource1)
        userService.addExternalLink(user.userId, resource2)

        val resources = userService.findUserByPhone(phone)!!.externalLinks
        log.info("resources: $resources")
        assertEquals(2, resources.size)
        assertEquals(resource1, resources[0].resourceName)
        assertEquals(resource2, resources[1].resourceName)
    }

    @Test
    fun changeGender() {
        val phone = RandomUtils.nextLong()
        val user = userService.auth(phone)

        userService.changeGender(user.userId, Gender.MALE)

        val found = userService.findUserByUserId(user.userId)!!
        assertEquals(Gender.MALE, found.gender)
//        assertEquals(1, user.version)
    }

    @Test
    fun changeCountryResidence() {
        val (cnrId, countryName) = countryRepository.save(Country(countryName = "Serbia"))
        val phone = RandomUtils.nextLong()
        val user = userService.auth(phone)

        userService.changeCountryResidence(user.userId, cnrId)

        val found = userService.findUserByUserId(user.userId)!!
        assertEquals(cnrId, found.countryResidence!!.cnrId)
        assertEquals(countryName, found.countryResidence!!.countryName)
    }

    @Test
    fun testAddName() {
        val phone = RandomUtils.nextLong()
        val user = userService.auth(phone)
        val name = "Sergey"

        userService.addName(user.userId, name)

        val found = userService.findUserByUserId(user.userId)!!
        assertEquals(name, found.names[0].firstName)
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
//            .withInitScript("db/manual")

        @JvmStatic
        @DynamicPropertySource
        fun postgreSQLProperties(registry: DynamicPropertyRegistry) {
            registry.add("db.url") { postgreSQLContainer.jdbcUrl }
            registry.add("db.user") { postgreSQLContainer.username }
            registry.add("db.password") { postgreSQLContainer.password }
            // use public schema as default for tests
            registry.add("spring.jpa.properties.hibernate.default_schema") { "public" }
        }
    }
}