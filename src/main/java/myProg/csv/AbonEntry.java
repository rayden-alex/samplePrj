package myProg.csv;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AbonEntry {

    @JsonProperty("as_code")
    private Integer regionId;

    @JsonProperty("account")
    private Integer account;

    @JsonProperty("account_check")
    private Integer accountCheck;

    @JsonProperty("phone_local")
    private String phoneLocal;

    @JsonProperty("phone_intern")
    private String phoneIntern;

    @JsonProperty("phone_category")
    private Integer phoneCategory;
    //   private Integer type;
    //   private Integer phone_features;
    //   private Integer territory_type;

    @JsonProperty("surname")
    private String surname;

    @JsonProperty("name")
    private String name;

    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty("contract_number")
    private String contractNumber;

    @JsonProperty("contract_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate contractDate;
    //   private Integer beneficiary;
    //   private Integer lg;
    //   private Integer gls;
    //   private Integer resident_code_ok;

    @JsonProperty("city_code")
    private Integer cityId;

    @JsonProperty("street_code")
    private Integer streetId;

    @JsonProperty("building")
    private String building;

    @JsonProperty("room")
    private String room;

    @JsonProperty("pind")
    private String pind;

    @JsonProperty("email")
    private String email;

    @JsonProperty("address")
    private String address;

    @JsonProperty("contact_phone_intern")
    private String contactPhone;

    @JsonProperty("date_r")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateR;

    @JsonProperty("date_close")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateClose;
}
