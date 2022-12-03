package rs.skurikhin.demo.hibernate

import org.slf4j.LoggerFactory
import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import kotlin.reflect.jvm.jvmName

@SpringBootApplication
class HibernateDemoMain {

    companion object {
        private val log = LoggerFactory.getLogger(HibernateDemoMain::class.jvmName)

        @JvmStatic
        fun main(args: Array<String>) {
            log.info("rs.skurikhin.demo.hibernate.HibernateDemoMain")
//            runApplication<HibernateDemoMain>(*args) {
//                setBannerMode(Banner.Mode.OFF)
//            }
            SpringApplication.run(HibernateDemoMain::class.java, *args)
        }
    }
}
