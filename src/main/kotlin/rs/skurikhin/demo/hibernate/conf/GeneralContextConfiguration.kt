package rs.skurikhin.demo.hibernate.conf

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDate
import kotlin.reflect.jvm.jvmName

@Configuration
class GeneralContextConfiguration {
    private val log = LoggerFactory.getLogger(GeneralContextConfiguration::class.jvmName)

    @Bean
    fun runner() = CommandLineRunner {
        log.info("Ran!, date={}", LocalDate.now())
    }
}