package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "CITY")
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "City")

public class City {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    private Integer id;

    @Column(name = "CITY_TYPE")
    private Short cityTypeId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "CITY_TYPE", nullable = false)
    private CityType cityType;

    @Column(name = "NAME")
    private String name;

}
