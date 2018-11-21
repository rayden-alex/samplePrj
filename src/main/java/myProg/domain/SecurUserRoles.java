package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "SecurUserRoles")

@Table(name="SECUR_USER_ROLES")
public class SecurUserRoles {

  @Id
  @Column(name="ID", updatable = false, nullable = false)
  @SequenceGenerator(name = "secur_user_roles_gen", allocationSize = 1, sequenceName = "GEN_SECUR_USER_ROLES_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secur_user_roles_gen")
  private Long id;

  @Column(name="USER_ID")
  private Long userId;

  @Column(name="ROLE_ID")
  private Long roleId;

}
