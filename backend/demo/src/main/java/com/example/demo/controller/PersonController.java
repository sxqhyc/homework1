package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.common.Result;
import com.example.demo.entity.Person;
import com.example.demo.mapper.PersonMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
@RequestMapping("/person")
public class PersonController {

    @Resource
    PersonMapper personMapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody Person person){
        Person res = personMapper.selectOne(Wrappers.<Person>lambdaQuery().eq(Person::getUsername,person.getUsername()).eq(Person::getPassword, person.getPassword()));
        if(res == null){
            return Result.error("-1","用户名或密码错误");
        }
        return Result.success();
    }
    @PostMapping("/register")
    public Result<?> register(@RequestBody Person person){
        Person res = personMapper.selectOne(Wrappers.<Person>lambdaQuery().eq(Person::getUsername, person.getUsername()));
        if(res != null){
            return Result.error("-1","用户名重复");
        }
        if(person.getPassword() == null){
            person.setPassword("123456");
        }
        personMapper.insert(person);
        return Result.success();
    }
}
