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
public class subtype implements Serializable {
    private int id;
    private int typeId;
    private String subtypeName;
    private int minLevel;
    private int maxLevel;
}
