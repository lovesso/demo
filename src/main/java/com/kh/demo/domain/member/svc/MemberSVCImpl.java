package com.kh.demo.domain.member.svc;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service    //임플에 서비스 어노테이션하기
@RequiredArgsConstructor        //생성자를 자동으로 생성해줌 이거 있으면 @Autowired생성자 생략가능! 둘 중 하나만 써야한다.
public class MemberSVCImpl implements MemberSVC {

  private final MemberDAO memberDAO;       // "나 컨트롤러에서 회원정보를 받았어" 그리고 그 정보를 줄 DAO알아야하기에 DAO변수를 선언.
  //만약 DAOImple 이 2개이상인데 구별하는 이름이 없으면 스프링부트는 어떤 DAO인지 알수 없다.

//  @Autowired //Alt + insert        //생성자를 생성해서 DAO 주입받도록하기
//  public MemberSVCImpl(MemberDAO memberDAO) {
//    this.memberDAO = memberDAO;
//  }

  //case2)
//  @Autowired //Alt + insert
//  public MemberSVCImpl(@Qualifier("memberDAOImpl2") MemberDAO memberDAO) {    //memberDAOImpl2 생성자 주입
//    this.memberDAO = memberDAO;
//    log.info("memberDAO={}", memberDAO.getClass().getName());
//  }

  //회원가입
  @Override
  public Long joinMember(Member member) {
    return memberDAO.insertMember(member);
  }

  //회원조회
  public boolean existMemberId(String email) {
    return memberDAO.existMemberId(email);
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    return memberDAO.findByEmailAndPasswd(email, passwd);
  }
}














