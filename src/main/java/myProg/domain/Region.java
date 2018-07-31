package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REGION")
@NoArgsConstructor
@Setter
@Getter
@ToString

@JsonRootName(value = "Region")

public class Region {

    @Id
    @Column(name = "ID", updatable = false, insertable = false, nullable = false)
    private Short id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE_PREFIX")
    private String phonePrefix;

}
