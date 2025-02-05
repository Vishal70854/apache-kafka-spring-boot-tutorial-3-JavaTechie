package com.javatechie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data   // it will include getter, setter and many functionalities by adding @Data annotation
@AllArgsConstructor
public class Customer {

    private int id;
    private String name;
    private String email;
    private String contactNo;

}
