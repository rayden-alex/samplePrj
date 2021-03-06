package myProg.domain;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.FETCH_SIZE_PARAM;
import static com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator.SEQUENCE_PARAM;


@Entity
@Table(name = "ABON")

@NoArgsConstructor
@Setter
@Getter
@ToString

@XmlRootElement(name = "abon") // JAXB annotation - not working
// Jackson annotation - it works depends on precedence of `AnnotationIntrospector's included (Jackson's own vs JAXB).
@JsonRootName(value = "abon")
public class Abon {
    @Id
    @GenericGenerator(
            name = "abonIdSequence",
            strategy = "com.github.marschall.hibernate.batchsequencegenerator.BatchSequenceGenerator",
            parameters = {
                    @Parameter(name = SEQUENCE_PARAM, value = "ABON_ID_SEQ"),
                    @Parameter(name = FETCH_SIZE_PARAM, value = "20")
            })
    @GeneratedValue(generator = "abonIdSequence")
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id;

    @Column(name = "REGION")
    private Short region;

    @Column(name = "ACCOUNT")
    private Integer account;

    @Column(name = "PHONE_LOCAL")
    private String phoneLocal;

    @Column(name = "PHONE_INTERN")
    private String phoneIntern;

    @Column(name = "SURNAME")
    private String surname;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PATRONYMIC")
    private String patronymic;

    @Column(name = "FIO")
    private String fio;

    @Column(name = "CITY_CODE")
    private Integer cityCode;

    @Column(name = "STREET_CODE")
    private Integer streetCode;

    @Column(name = "BUILDING")
    private String building;

    @Column(name = "ROOM")
    private String room;

    @Column(name = "PIND")
    private String pind;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE_CLOSE")
    private LocalDate dateClose;

    @Column(name = "DATE_R")
    private LocalDateTime dateR;

    @Column(name = "DATE_CLOSE_TXT")
    private String dateCloseTxt;

    @Column(name = "DATE_R_TXT")
    private String dateRTxt;


    // If you’re dealing with an ORM, make sure to always use getters,
    // and never field references in hashCode() and equals().
    // This is for reason, in ORM, occasionally fields are lazy loaded
    // and not available until called their getter methods
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Abon)) return false;
        final Abon other = (Abon) o;

        return other.canEqual(this)
                && (getId() != null)
                && Objects.equals(getId(), other.getId());
    }

    @Override
    public int hashCode() {
        return 43;
    }

    protected boolean canEqual(Object other) {
        return (other instanceof Abon);
    }

//    https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
//    https://www.artima.com/lejava/articles/equality.html

//    @Override
//    public int hashCode() {
//        // hashCode не должен меняться после сохранения entity,
//        // но т.к. присваевается id, то надо либо включать все поля кроме id
//        // (что может дать коллизию при двух одинаковых записях в БД различающихся только id)
//        // или не включать вообще ни одного поля (проблемы только при ОЧЕНЬ больших мапах)
//        // By the time the constant hashCode becomes your bottleneck,
//        // you had to fetch millions of records which already took a lot of time.
//        return 31;
//    }

}


// Чтобы работать с полями типа LocalDateTime (Java 8)
// надо подключить dependency   compile group: 'org.hibernate', name: 'hibernate-java8', version: '5.2.12.Final'
// или написать свой attribute converter :

//@Converter(autoApply = true)
//public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {
//
//    @Override
//    public Timestamp convertToDatabaseColumn(LocalDateTime locDateTime) {
//        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
//    }
//
//    @Override
//    public LocalDateTime convertToEntityAttribute(Timestamp sqlTimestamp) {
//        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
//    }
//}


