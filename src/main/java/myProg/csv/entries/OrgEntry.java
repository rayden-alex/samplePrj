package myProg.csv.entries;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrgEntry {

    @JsonProperty("as_code")
    private Integer regionId;

    @JsonProperty("account")
    private Integer account;

    @JsonProperty("name")
    private String name;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("address")
    private String address;

    @JsonProperty("unp")
    private String unp;

    @JsonProperty("bank_mfo")
    private String bankMfo;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("rs_number")
    private String rsNumber;

    @JsonProperty("bank_code")
    private String bankCode;

    @JsonProperty("bank_dep_num")
    private String bankDepNum;

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

    @JsonProperty("contact_phone_intern")
    private String contactPhone;

    @JsonProperty("date_close")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateClose;

    @JsonProperty("date_r")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateR;
}
