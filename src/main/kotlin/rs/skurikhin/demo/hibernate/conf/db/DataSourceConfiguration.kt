package rs.skurikhin.demo.hibernate.conf.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataSourceConfiguration {

    @Bean
    fun dataSource(
        @Value("\${db.url}") url: String,
        @Value("\${db.user}") user: String,
        @Value("\${db.password}") password: String,
    ): HikariDataSource {
        val config = HikariConfig().also {
            it.jdbcUrl = url
            it.username = user
            it.password = password
        }
        return HikariDataSource(config)
    }
}