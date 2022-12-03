package rs.skurikhin.demo.hibernate.web

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import rs.skurikhin.demo.hibernate.service.UserService
import kotlin.random.Random

@RestController
@RequestMapping("users")
class UserController(
    @Autowired var userService: UserService
) {

    @PostMapping
    fun insertUser(): ResponseEntity<*> {
        val phone = Random.nextLong()
        log.info("Insert user with phone={}", phone)

        val res = userService.auth(phone)
        return ResponseEntity.ok(res)
    }

    companion object Abc {
        val log = LoggerFactory.getLogger(UserController::class.java.name)
    }
}