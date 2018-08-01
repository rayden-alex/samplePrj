package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "CITY_TYPE")
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "CityType")

public class CityType {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

}
