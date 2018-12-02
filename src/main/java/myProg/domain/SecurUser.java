package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "SecurUser")

@Table(
        name = "SECUR_USER",
        //These "Constraint" are only used if table generation is in effect.
        uniqueConstraints = @UniqueConstraint(columnNames = "LOGIN"))
public class SecurUser {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @SequenceGenerator(name = "secur_user_gen", allocationSize = 1, sequenceName = "GEN_SECUR_USER_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secur_user_gen")
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "SECUR_USER_ROLES",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID"))
    private Collection<SecurRole> roles;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LOGIN")
    private String login;

    @Column(name = "PASS")
    private String pass;

    @Column(name = "E_MAIL")
    private String eMail;

}
