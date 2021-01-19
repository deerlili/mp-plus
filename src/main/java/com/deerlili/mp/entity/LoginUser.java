package com.deerlili.mp.entity;

import lombok.Data;

@Data
public class LoginUser {

    private String username;
    private String password;
    private Long rememberMe;

}
