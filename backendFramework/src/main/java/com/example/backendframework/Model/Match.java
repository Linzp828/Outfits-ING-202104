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
public class Match implements Serializable {
    private int id;
    private int occasion_id;
    private String introduce;
    private int user_id;

    public Match(int occasion_id,String introduce,int user_id){
        this.id=-1;
        this.occasion_id=occasion_id;
        this.introduce=introduce;
        this.user_id=user_id;
    }

    public int getId(){
        return this.id;
    }
}
