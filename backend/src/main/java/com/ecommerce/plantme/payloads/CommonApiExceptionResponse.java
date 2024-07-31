package com.ecommerce.plantme.payloads;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonApiExceptionResponse {

    private int status;
    private String message;
    private long timeStamp;

}

