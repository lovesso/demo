package com.kh.demo.web;

import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@AllArgsConstructor
@Getter
class Person{
  String name;
  int age;
}

@AllArgsConstructor
@Getter
class Test<T>{
  T data;
}

@Slf4j
@Controller
@RequestMapping("/api/test")
public class TestController4 {

  @ResponseBody
  @GetMapping("t1")
  public String t1(){
    return "{ \"name\":\"홍길동\", \"age\":\"20\" }";

  }

  @ResponseBody
  @GetMapping("t3")
  public Person t3(){
    Person p1 = new Person("양제나", 20);
        return p1;
  }


  @ResponseBody
  @GetMapping("t4/map")
  public Map<String,Person> map(){
    Map<String,Person> map = new HashMap<>();
    map.put("1", new Person("양제나", 10));
    map.put("2", new Person("김기린", 20));
    map.put("3", new Person("박상구", 30));
    return map;
  }

  @ResponseBody
  @GetMapping("t4/set")
  public Set<Person> set(){
    Set<Person> set = new HashSet<>();

    set.add(new Person("양제나", 10));
    set.add( new Person("김기린", 20));
    set.add(new Person("박상구", 30));

    return set;
  }

  @ResponseBody
  @GetMapping("t4/list")
  public List<Person> list(){


    List<Person> list = new ArrayList<>();

    list.add(new Person("양제나", 10));
    list.add( new Person("김기린", 20));
    list.add(new Person("박상구", 30));

    return list;
  }

  @ResponseBody
  @GetMapping("t4/list2")
  public Test<List<Person>> list2(){ //객체로 한번 감쌈


    List<Person> list = new ArrayList<>();

    list.add(new Person("양제나", 10));
    list.add( new Person("김기린", 20));
    list.add(new Person("박상구", 30));

    Test<List<Person>> test = new Test<>(list);
    return test;
  }
}
