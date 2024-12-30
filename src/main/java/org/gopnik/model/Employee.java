package org.gopnik.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "employees")
@Setter
@Getter
public class Employee { // alias "DrugDealer"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nazwa użytkownika jest wymagana")
    private String username;

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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    @JsonIgnore // udajemy ze tego nie widzimy
    private Cart cart;


    @Override
    public String toString() {
        return String.format("User: %s, Aktualna apteka: %s", this.getUsername(), this.getDrugstoreId());
    }


}
