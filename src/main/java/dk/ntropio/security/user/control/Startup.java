package dk.ntropio.security.user.control;

import dk.ntropio.security.user.entity.Role;
import dk.ntropio.security.user.entity.User;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.Arrays;


@Singleton
public class Startup {

    @Transactional
    public void loadUsers(@Observes StartupEvent evt) {
        // reset and load all test users
        User.deleteAll();
        Role.deleteAll();

        Role admin = createRole("admin");
        createRole("user");

        User user = UserController.addUser("admin", "admin");
    }

    private Role createRole(String sRole) {
        Role userRole = new Role();
        userRole.role = sRole;
        userRole.persist();
        return userRole;
    }
}