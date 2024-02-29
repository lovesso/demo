package com.kh.demo.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.web.form.member.JoinForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

  private final MemberSVC memberSVC;
  
  //회원가입양식
  @GetMapping("/join")      //제이슨으로 확인가능 get방식으로 http://localhost:9080/members/join
  public String joinForm(){
    return "member/joinForm";
  }

  //회원가입처리
  @PostMapping("/join")     //제이슨으로 확인가능 post방식으로 http://localhost:9080/members/join , body, www방식으로 키 밸류 적어서
  public String join(JoinForm joinForm){   //JoinForm 사용자정의클래스 회원가입폼 객체
    log.info("joinForm={}, joinForm"); //joinForm이 제대로 들어왔는지 확인(서버에 회원가입 양식 적어서 보내봄)

        //1)유효성 검증


        //2)가입처리
    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);
    Long memberId = memberSVC.joinMember(member); //상단에 MemberSVC 객체 생성안해서 memberSVC에 빨간줄 생겼음,
                    // ======> 이 한줄 빼먹으니까 회원가입창에 가입 시도시, 디비에 연동안되서 저장안됨. 작동은 됨.
    return "redirect:/"; //회원가입이 처리되면 메인화면으로
  }
}