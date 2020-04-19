package com.find;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
public class Transcation {
    private Trader trader;
    private Integer year;
    private Integer value;

    public static Integer test(Integer value){
        return value;
    }
}
