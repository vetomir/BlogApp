package pl.gregorymartin.blogapp.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.gregorymartin.blogapp.user.Role;
import pl.gregorymartin.blogapp.user.User;
import pl.gregorymartin.blogapp.user.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserApi {
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserApi.class);

    public UserApi(final UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/all")
    ResponseEntity<List<User>> readAllUsers() {
        logger.warn("Exposing all the people!");
        return ResponseEntity.ok(userService.showAllUsers());
    }

    @GetMapping("/roles")
    ResponseEntity<List<Role>> readAllRoles() {
        logger.warn("Exposing all the people!");
        return ResponseEntity.ok(userService.showAllRoles());
    }

    @PostMapping("/add")
    ResponseEntity<User> createAppUser(@RequestBody @Valid User toCreate, @RequestHeader(defaultValue = "") String role) {
        User result = userService.addUser(toCreate, role);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }


    @PostMapping("/role/{role}")
    ResponseEntity<?> createRole(@PathVariable String role) {
        userService.createRole(role);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User toUpdate) {
        userService.updateUser(id,toUpdate);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteUser(@PathVariable long id) {
        boolean result = userService.deleteApp(id);
        if(result){
            return ResponseEntity.noContent().build();
        }
        else
            return ResponseEntity.notFound().build();
    }


}
