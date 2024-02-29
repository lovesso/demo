package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;


@Slf4j
@SpringBootTest//테스트할때 스프링부트가 필요하기때문에 붙여야한다.
public class MemberDAOImplTest {

  @Autowired     //직접생성하지않고 주입받기 위해.  //구현객체는 여러개 생성가능하다.
  //@Qualifier("memberDAOImpl2")
  MemberDAO memberDAO;


  @Test
  void test() {
    log.info("memberDAO={}", memberDAO.getClass().getName());
  }

  @Test
  @DisplayName("회원가입")
  void insertMember() {
    Member member = new Member();
    member.setEmail("user3@kh.com");
    member.setPasswd("user3");
    member.setNickname("사용자3");
    Long memberId = memberDAO.insertMember(member);
    log.info("memberId={}}", memberId);  //테스트 돌려보면 오라클에 사용자3이 등록된다. 성공!
  }

  //이메일(아이디) 존재유무 테스트는 존재하는 경우와 존재안하는 경우 2가지를 전부 테스트해야한다.
  @Test
  @DisplayName("이메일(O)")
  void existMemberId() {
    boolean exit = memberDAO.existMemberId("sl1@gmail.com");
    Assertions.assertThat(exit).isEqualTo(true); //여기서 exit 은 실제값 true 는 예상값
  }

  @Test
  @DisplayName("이메일존재(X)")
  void notExistEmail() {
    boolean exit = memberDAO.existMemberId("sl133@gmail.com");
    Assertions.assertThat(exit).isEqualTo(false); //여기서 exit 은 실제값 false 는 예상값
  }

  @Test
  @DisplayName("회원조회(O)")
  void findByEmailAndPasswd() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("sl1@gmail.com", "user1");
    // 결과 검증
    Assertions.assertThat(optionalMember).isPresent(); // Optional이 존재해야 함

    Member findedMember = optionalMember.get();
    Assertions.assertThat(findedMember.getEmail()).isEqualTo("sl1@gmail.com"); // 이메일 일치 여부 확인
    Assertions.assertThat(findedMember.getPasswd()).isEqualTo("user1"); // 비밀번호 일치 여부 확인
  }

  @Test
  @DisplayName("회원조회(X)")
  void findByEmailAndPasswd2() {
    Optional<Member> optionalMember = memberDAO.findByEmailAndPasswd("user9@kh.com", "user1");
    // 결과 검증
    Assertions.assertThat(optionalMember).isEmpty(); // Optional이 없어 함
  }
}

