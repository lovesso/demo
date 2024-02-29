package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberDAO {
  // 1) 가입, 2) 조회, 3) 수정, 4) 탈퇴

  //1) 회원가입                                 // layer간의 정보는 객체에 담아서 전달한다(controller -> svc -> dao 이동시)
  Long insertMember(Member member);           //entity클래스 Member 정보가 들어와야한다.

  //2) 회원조회

  //   2-1) 아이디 조회
  boolean existMemberId(String email);  //input은 email, output은 중복유무는 2가지 결과값으로만 출력되니깐 boolean으로.

  //
  Optional<Member> findByEmailAndPasswd(String email, String passwd);



  }


  //3) 회원수정
  //4) 회원탈퇴

