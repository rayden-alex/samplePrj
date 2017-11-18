package myProg.jpa;

import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ABON")
@ToString
public class AbonEntity {
    private Long  id;
    private Short region;
    private Integer account;
    private String phoneLocal;
    private String phoneIntern;
    private String surname;
    private String name;
    private String patronymic;
    private String fio;
    private Integer cityCode;
    private Integer streetCode;
    private String building;
    private String room;
    private String pind;
    private String address;
    private LocalDate dateClose;
    private LocalDateTime dateR;
    private String dateCloseTxt;
    private String dateRTxt;

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long  getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "REGION")
    public Short getRegion() {
        return region;
    }

    public void setRegion(Short region) {
        this.region = region;
    }

    @Basic
    @Column(name = "ACCOUNT")
    public Integer getAccount() {
        return account;
    }

    public void setAccount(Integer account) {
        this.account = account;
    }

    @Basic
    @Column(name = "PHONE_LOCAL")
    public String getPhoneLocal() {
        return phoneLocal;
    }

    public void setPhoneLocal(String phoneLocal) {
        this.phoneLocal = phoneLocal;
    }

    @Basic
    @Column(name = "PHONE_INTERN")
    public String getPhoneIntern() {
        return phoneIntern;
    }

    public void setPhoneIntern(String phoneIntern) {
        this.phoneIntern = phoneIntern;
    }

    @Basic
    @Column(name = "SURNAME")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PATRONYMIC")
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Basic
    @Column(name = "FIO")
    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    @Basic
    @Column(name = "CITY_CODE")
    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }

    @Basic
    @Column(name = "STREET_CODE")
    public Integer getStreetCode() {
        return streetCode;
    }

    public void setStreetCode(Integer streetCode) {
        this.streetCode = streetCode;
    }

    @Basic
    @Column(name = "BUILDING")
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    @Basic
    @Column(name = "ROOM")
    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Basic
    @Column(name = "PIND")
    public String getPind() {
        return pind;
    }

    public void setPind(String pind) {
        this.pind = pind;
    }

    @Basic
    @Column(name = "ADDRESS")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "DATE_CLOSE")
    public LocalDate getDateClose() {
        return dateClose;
    }

    public void setDateClose(LocalDate dateClose) {
        this.dateClose = dateClose;
    }

    @Basic
    @Column(name = "DATE_R")
    public LocalDateTime getDateR() {
        return dateR;
    }

    public void setDateR(LocalDateTime dateR) {
        this.dateR = dateR;
    }

    @Basic
    @Column(name = "DATE_CLOSE_TXT")
    public String getDateCloseTxt() {
        return dateCloseTxt;
    }

    public void setDateCloseTxt(String dateCloseTxt) {
        this.dateCloseTxt = dateCloseTxt;
    }

    @Basic
    @Column(name = "DATE_R_TXT")
    public String getDateRTxt() {
        return dateRTxt;
    }

    public void setDateRTxt(String dateRTxt) {
        this.dateRTxt = dateRTxt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbonEntity)) return false;

        AbonEntity that = (AbonEntity) o;

        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (phoneLocal != null ? !phoneLocal.equals(that.phoneLocal) : that.phoneLocal != null) return false;
        if (phoneIntern != null ? !phoneIntern.equals(that.phoneIntern) : that.phoneIntern != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (patronymic != null ? !patronymic.equals(that.patronymic) : that.patronymic != null) return false;
        if (fio != null ? !fio.equals(that.fio) : that.fio != null) return false;
        if (cityCode != null ? !cityCode.equals(that.cityCode) : that.cityCode != null) return false;
        if (streetCode != null ? !streetCode.equals(that.streetCode) : that.streetCode != null) return false;
        if (building != null ? !building.equals(that.building) : that.building != null) return false;
        if (room != null ? !room.equals(that.room) : that.room != null) return false;
        if (pind != null ? !pind.equals(that.pind) : that.pind != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (dateClose != null ? !dateClose.equals(that.dateClose) : that.dateClose != null) return false;
        if (dateR != null ? !dateR.equals(that.dateR) : that.dateR != null) return false;
        if (dateCloseTxt != null ? !dateCloseTxt.equals(that.dateCloseTxt) : that.dateCloseTxt != null) return false;
        return dateRTxt != null ? dateRTxt.equals(that.dateRTxt) : that.dateRTxt == null;
    }

    @Override
    public int hashCode() {
        int result = region != null ? region.hashCode() : 0;
        result = 31 * result + (account != null ? account.hashCode() : 0);
        result = 31 * result + (phoneLocal != null ? phoneLocal.hashCode() : 0);
        result = 31 * result + (phoneIntern != null ? phoneIntern.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (fio != null ? fio.hashCode() : 0);
        result = 31 * result + (cityCode != null ? cityCode.hashCode() : 0);
        result = 31 * result + (streetCode != null ? streetCode.hashCode() : 0);
        result = 31 * result + (building != null ? building.hashCode() : 0);
        result = 31 * result + (room != null ? room.hashCode() : 0);
        result = 31 * result + (pind != null ? pind.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (dateClose != null ? dateClose.hashCode() : 0);
        result = 31 * result + (dateR != null ? dateR.hashCode() : 0);
        result = 31 * result + (dateCloseTxt != null ? dateCloseTxt.hashCode() : 0);
        result = 31 * result + (dateRTxt != null ? dateRTxt.hashCode() : 0);
        return result;
    }
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
