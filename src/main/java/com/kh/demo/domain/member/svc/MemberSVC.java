package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;

import java.util.Optional;

public interface MemberSVC {
  // 1) 가입, 2) 조회, 3) 수정, 4) 탈퇴
  Long joinMember(Member member);

  //2) 회원 아이디 조회
  boolean existMemberId(String email);

  //회원조회
  Optional<Member> findByEmailAndPasswd(String email, String passwd);
  //3) 회원수정
  //4) 회원탈퇴
}
