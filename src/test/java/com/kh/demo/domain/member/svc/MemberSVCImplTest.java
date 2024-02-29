package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
@Slf4j
@SpringBootTest
public class MemberSVCImplTest {

  @Autowired      //테스트하려면 객체가 필요하고 객체를 주입받아야한다.
  MemberSVC memberSVC;

  @Test  //회원가입 테스트
  @DisplayName("회원가입") //테스트 실행후 좌측 하단의 테스트실행 이름을 바꿔줌
  @Transactional  //테스트환경일땐 실행 후 rollback된다(즉 데이터가 insert되지 않는다),이거안하니까 서버 재시작때마다 유저4가 생긴다.
  void joinMember(){
    Member member = new Member();  //엔터티의 멤버 객체
    member.setEmail("user4kh.com");
    member.setPasswd("user4");
    member.setNickname("사용자4");
    Long memberId = memberSVC.joinMember(member);
    log.info("member={}, memberId"); //작성 후 테스트 실행. (오라클에 사용자4가 저장된다. 근데 난 왜 안되지?)
                                      //멤버서비스 joinMember의 리턴값이 null이었음 DAO로 바꾸니까 저장되었다.
  }
}
