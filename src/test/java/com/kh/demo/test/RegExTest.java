package com.kh.demo.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

@Slf4j
public class RegExTest {

  @Test
  void t1(){
    String str = "12";
    String pattern = "[0-9]{3,10}";

//    if(Pattern.matches(pattern,str)){
    if(Pattern.matches("\\d{3,10}","1232")){
      log.info("패턴일치");
    }else{
      log.info("패턴불일치");
    }
  }
  @Test
  void t2(){
    if(Pattern.matches("\\d{3}-\\d{4}-\\d{4}","01012345678")){
      log.info("패턴일치");
    }else{
      log.info("패턴불일치");
    }
  }
}
