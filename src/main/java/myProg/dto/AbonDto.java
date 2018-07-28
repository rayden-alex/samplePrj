package myProg.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class AbonDto implements Serializable {
    private Long id;
    private String fio;
    private String phone;

}
