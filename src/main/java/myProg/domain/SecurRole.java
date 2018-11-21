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

@JsonRootName(value = "SecurRole")

@Table(name="SECUR_ROLE")
public class SecurRole {

  @Id
  @Column(name="ID", updatable = false, nullable = false)
  @SequenceGenerator(name = "secur_role_gen", allocationSize = 1, sequenceName = "GEN_SECUR_ROLE_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secur_role_gen")
  private Long id;

  @Column(name="NAME")
  private String name;

}
