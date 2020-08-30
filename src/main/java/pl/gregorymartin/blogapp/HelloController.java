package pl.gregorymartin.blogapp;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.gregorymartin.blogapp.api.UserApi;
import pl.gregorymartin.blogapp.user.Role;

import java.util.List;

@RestController
@NoArgsConstructor
class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping
    ResponseEntity<String> readAllRoles() {
        logger.warn("Exposing all the people!");
        return ResponseEntity.ok("Nara");
    }

}
