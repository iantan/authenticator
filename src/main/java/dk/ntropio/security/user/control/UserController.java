package dk.ntropio.security.user.control;

import dk.ntropio.security.user.entity.Role;
import dk.ntropio.security.user.entity.User;
import io.quarkus.elytron.security.common.BcryptUtil;

import javax.transaction.Transactional;
import java.util.Arrays;

public class UserController {

    public static boolean isValidUserName(String userName) {
        return !User.isPresent(userName);
    }

    @Transactional
    public static User addUser(String userName, String password) {
        Role role = Role.findByRole("user");
        User user = User.create(userName, password, Arrays.asList(role));
        return user;
    }

    @Transactional
    public static void changePassword(String userName, String password) {
        User user = User.findByUserName(userName);
        user.password = BcryptUtil.bcryptHash(password);
    }

}
