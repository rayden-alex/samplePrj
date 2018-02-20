package myProg.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Abon implements Serializable {
    private Long id;
    private String fio;
    private String phone;

}
