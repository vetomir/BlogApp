package pl.gregorymartin.blogapp.user;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(final UserRepository userRepository, final RoleRepository roleRepository, final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> showAllUsers(Pageable pageable){
        return userRepository.findAll(pageable);
    }

    public User addUser(User toAdd, String role){
        logger.info("Exposing all the Users");

        toAdd.setPassword(passwordEncoder.encode(toAdd.getPassword()));
        List<Role> roleArray = new ArrayList<>();
        Optional<Role> role1 = roleRepository.findByName("ROLE_USER");
        roleArray.add(role1.get());

        if(!role.isEmpty()){
            Optional<Role> role2 = roleRepository.findByName("ROLE_" + role.toUpperCase());

            role2.ifPresent(roleArray::add);
        }

        toAdd.setRoles(roleArray);
        logger.info("User " + toAdd.getName() + ". Created!");
        return userRepository.save(toAdd);
    }

    public User updateUser(Long id, User toUpdate){
        Optional<User> byId = userRepository.findById(id);

        byId.ifPresent(user -> {
            user.toUpdate(toUpdate);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        });

        logger.info("User " + byId.get().getName() + ". Updated!");
        return userRepository.findById(id).get();
    }

    public User createRole(String role, Long id){
        role = "ROLE_" + role.toUpperCase();
        Optional<Role> existsRole = roleRepository.findByName(role);
        User user = userRepository.findById(id).get();
        if(existsRole.isPresent()){
            existsRole.get().getUsers().add(user);
        }
        else {
            Role newRole = new Role(role);
            user.getRoles().add(newRole);
        }
        return user;
    }
    public boolean deleteRole(String role){
        role = "ROLE_" + role.toUpperCase();
        final AtomicBoolean result = new AtomicBoolean(false);
        Optional<Role> byName = roleRepository.findByName(role);
        byName.ifPresent(x -> {
            roleRepository.deleteById(x.getId());
            result.set(true);
        });
        return result.get();
    }

    public boolean deleteAppUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            logger.info("User with id: " + id +". Deleted!");
        }
        return !userRepository.existsById(id);
    }
}
