package com.kh.demo.web.form.member;

import lombok.Data;

@Data    //요청데이터를 담기위해 만든 클래스이다.
public class JoinForm {   //회원가입 폼 joinForm에서 네임속성있는것들
  private String email;
  private String passwd;
  private String nickname;
}
