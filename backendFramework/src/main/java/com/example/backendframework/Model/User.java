package com.example.backendframework.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@ToString
public class User implements Serializable {
    private int id;
    private String user_account;
    private String user_password;
    private String user_nickname;
    private String user_sex;
    private String user_pic_path;
    private String user_profile;
    private String user_token;
}
