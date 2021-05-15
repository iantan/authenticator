package dk.ntropio.security.user.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.RolesValue;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.List;

@Entity
public class Role extends PanacheEntity {

    @ManyToMany(mappedBy = "roles")
    public List<User> users;

    @RolesValue
    public String role;

    public static Role findByRole(String role) {
        return find("role", role).singleResult();
    }
}
