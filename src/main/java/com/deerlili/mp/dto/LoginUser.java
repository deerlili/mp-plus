package com.deerlili.mp.dto;

import lombok.Data;

@Data
public class LoginUser {

    private String username;
    private String password;
    private Long rememberMe;

}
