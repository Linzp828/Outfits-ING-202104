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
public class Occasion implements Serializable {
    private int id;
    private String occasion_name;
    private int user_id;
}
