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
    private String userAccount;
    private String userPassword;
    private String userNickname;
    private String userSex;
    private String userPicPath;
    private String userProfile;
}
