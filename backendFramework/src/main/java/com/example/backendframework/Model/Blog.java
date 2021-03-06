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
public class Blog implements Serializable {
    private int id;
    private String blog_article;
    private String blog_pic_path;
    private String blog_title;
    private String blog_released_time;
    private int user_id;
}
