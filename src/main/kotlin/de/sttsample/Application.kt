package de.sttsample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
@EnableSwagger2 // test swagger-doc: http://localhost:8080/swagger-ui.html
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
