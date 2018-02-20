package myProg.jpa.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ABON")

@Data
@NoArgsConstructor
public class AbonEntity {
    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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


//    @Override
//    public int hashCode() {
//        // hashCode не должен меняться после сохранения entity,
//        // но т.к. присваевается id, то надо либо включать все поля кроме id
//        // (что может дать коллизию при двух одинаковых записях в БД различающихся только id)
//        // или не включать вообще ни одного поля (проблемы только при ОЧЕНЬ больших мапах)
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


