package myProg.csv.entries;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeptEntry {

    @JsonProperty("as_code")
    private Integer regionId;

    @JsonProperty("account")
    private Integer account;

    @JsonProperty("subaccount")
    private Integer subAccount;

    @JsonProperty("phone_local")
    private String phoneLocal;

    @JsonProperty("phone_intern")
    private String phoneIntern;

    @JsonProperty("phone_category")
    private Integer phoneCategory;

    @JsonProperty("dep_name")
    private String depName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("city_code")
    private Integer cityId;

    @JsonProperty("street_code")
    private Integer streetId;

    @JsonProperty("building")
    private String building;

    @JsonProperty("room")
    private String room;

    @JsonProperty("address")
    private String address;

    @JsonProperty("date_r")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateR;
}
