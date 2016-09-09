package es.mira.progesin.persistence.entities;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Builder
@ToString
@Getter
@Setter
@Entity
@Table(name="USER2")
public class User {
    @Id
    @Size(min=1, max=10, message="user.username.size")
    protected String username;

    @NotNull
    @Size(min=1, max=10, message="user.password.size")
    protected String password;

    @NotNull
    @Size(min=1, max=10, message="user.nombre.size")
    protected String nombre;

    @NotNull
    @Size(min=1, max=10, message="user.apellido1.size")
    protected String apellido1;

    @NotNull
    @Size(min=1, max=10, message="user.apellido2.size")
    protected String apellido2;

    @NotNull
    protected RoleEnum role;


}

