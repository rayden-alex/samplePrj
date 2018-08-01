package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "STREET")
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "Street")

public class Street {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

//    @Column(name = "STREET_TYPE")
//    private Short streetTypeId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "STREET_TYPE", nullable = false)
    private StreetType streetType;

    @Column(name = "NAME")
    private String name;

}
