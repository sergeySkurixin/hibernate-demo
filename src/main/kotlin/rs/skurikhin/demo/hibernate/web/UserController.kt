package rs.skurikhin.demo.hibernate.web

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import rs.skurikhin.demo.hibernate.bean.UserEntity
import rs.skurikhin.demo.hibernate.bean.request.CreateUserRequest
import rs.skurikhin.demo.hibernate.service.UserService

@RestController
@RequestMapping("users")
class UserController(
    @Autowired var userService: UserService
) {

    @PostMapping
    fun insertUser(@RequestBody req: CreateUserRequest): ResponseEntity<*> {
        log.info("Insert user: {}", req)

        val res = userService.auth(req.phone)
        return ResponseEntity.ok(res)
    }

    @GetMapping
    fun findAllUsers(): ResponseEntity<*> {
        log.info("Get all users")

        val res = userService.findAllUsers()
        return ResponseEntity.ok(res)
    }

    @GetMapping("{userId}")
    fun findByUserId(@PathVariable userId: Long): ResponseEntity<*> {
        log.info("Get user with userId={}", userId)

        val res = userService.findUserByUserId(userId)
        return ResponseEntity.ok(res)
    }

    @GetMapping("/phone/{phone}")
    fun findByPhone(@PathVariable phone: Long): ResponseEntity<*> {
        log.info("Get user with phone={}", phone)

        val res = userService.findUserByUserId(phone)
        return ResponseEntity.ok(res)
    }

    @PostMapping("/{userId}/add_article/{url}")
    fun addPhone(@PathVariable userId: Long, @PathVariable url: String): ResponseEntity<UserEntity> {
        log.info("Add article, userId={}, url='{}'", userId, url)

        val res = userService.addArticle(userId, url)
        return ResponseEntity.ok(res)
    }

    companion object Abc {
        val log = LoggerFactory.getLogger(UserController::class.java.name)
    }
}