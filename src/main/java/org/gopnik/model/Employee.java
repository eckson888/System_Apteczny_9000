package org.gopnik.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa użytkownika jest wymagana")
    private String username;

    @Setter
    private Long drugstoreId; // na razie cos takiego bo nw jak chcemy to zrobic

    @NotBlank(message = "Hasło jest wymagane")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Long getId() {
        return this.id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public Long getDrugstoreId() {
        return drugstoreId;
    }

    @Override
    public String toString() {
        return String.format("User: %s DrugstoreID: %s", this.getUsername(), this.getDrugstoreId());
    }
}
