package com.example.demo.domain.userdomain;
import com.example.demo.core.EntityBase;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("users")
public @NoArgsConstructor @Getter @Setter class User extends EntityBase{

    @Column("firstname")
    private String firstname;
    @Column("lastname")
    private String lastname;
    @Column("email")
    private String email;
    @Column("password")
    private String password;
    @Column("rol")
    private String rol;
    @Column("provider")
    private String provider;

    @Override
    public String toString() {

        return String.format("User {id: %s, name: %s, lastName: %s, email: %s, rol: %s}", this.getId(), this.getFirstname(),
                this.getLastname(), this.getEmail(), this.getRol());
    }

    @Override
    public boolean isNew() {
        return this.isThisNew();
    }
}
