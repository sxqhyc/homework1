package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("person")
@Data
public class Person {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
}
