package com.ck.cafe_back.wrapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class userWrapper {
    private Integer id;
    private String name;
    private  String email;
    private String contactNumber;
    private String status;


}
