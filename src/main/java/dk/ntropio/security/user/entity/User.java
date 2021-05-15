package dk.ntropio.security.user.entity;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_table")
@UserDefinition
public class User extends PanacheEntity {

    @Username
    public String userName;

    @Password
    public String password;

    @ManyToMany
    @Roles
    public List<Role> roles = new ArrayList<>();

    protected User(){}

    public User(String userName, String password, List<Role> roles) {
        this.userName = userName;
        this.password = BcryptUtil.bcryptHash(password);
        this.roles = roles;

        isValid();
    }

    private void isValid() {
        if (isPresent(this.userName)) {
            throw new UserAlreadyExist(this.userName);
        }
    }

    @Transactional
    public static User create(String userName, String password, List<Role> roles) {
        User user = new User(userName, password, roles);
        user.persist();
        return user;
    }

    public static boolean isPresent(String userName) {
        return User.find("username", userName).singleResultOptional().isPresent();
    }

    public static User findByUserName(String userName) {
        return User.find("username", userName).singleResult();
    }

    class UserAlreadyExist extends RuntimeException{
        UserAlreadyExist(String userName) {
            super(String.format("User <%s> already exist.", userName));
        }
    }
}