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
    // При @GeneratedValue ID генерится ВСЕГДА, даже если его вручную указать.
    // Чтобы обойти - надо делать @GenericGenerator с переопределенной "strategy"
    // https://stackoverflow.com/questions/3194721/bypass-generatedvalue-in-hibernate-merge-data-not-in-db/8535006#8535006

//    @SequenceGenerator(name = "city_gen", allocationSize = 1, sequenceName = "CITY_SEQUENCE")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "city_gen")
    private Integer id;

//    @Column(name = "CITY_TYPE")
//    private Short cityTypeId;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "CITY_TYPE", nullable = false)
    private CityType cityType;

    @Column(name = "NAME")
    private String name;

}
