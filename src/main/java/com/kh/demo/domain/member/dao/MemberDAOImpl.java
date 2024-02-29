package com.kh.demo.domain.member.dao;

import com.kh.demo.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Repository// ("memberDAOImpl") 구현클래스이름 =>구현클래스가 많을경우, 고유이름적기
@RequiredArgsConstructor   //생성자 자동생성
//@Primary //동일 타입의 DAOImpl 객체가 2개 이상 존재시, 최우선 주입 어노테이션 (한 개면 안적어도 된다)
public class MemberDAOImpl implements MemberDAO {

  private final NamedParameterJdbcTemplate template;

  //회원가입
  @Override //Alt + insert
  public Long insertMember(Member member) {
    StringBuffer sql = new StringBuffer(); //ctrl+alt+v  //스트링버퍼: 문자를 생성해주기위해.
    sql.append("insert into member (member_id, email, passwd, nickname) "); //sql에서 정보를 가져온다. 하지만 바뀌는부분은 파라미터로 바꾸기
    sql.append("values(member_member_id_seq.nextval,:email,:passwd,:nickname) ");   //맨끝에 "(쌍따옴표)전에 한칸 띄우기. -> 파라미터는 엔터티 변수와 동일하게

    //sql실행
    //1)sql파라미터 매핑
    SqlParameterSource param = new BeanPropertySqlParameterSource(member);

    //2)변경된 레코드 정보를 읽어오는 용도
    KeyHolder keyHolder = new GeneratedKeyHolder(); //2)insert된 값을 가져올때 키홀더를 사용.

    //3)sql실행        // 어떤 컬럼을 읽어올지 =>  new String[]{"컬럼 이름"} : 컬럼이름을 키홀더에 넣어준다.
    template.update(sql.toString(),param,keyHolder,new String[]{"member_id"}); //template는 왜 빨간줄? 상단에 private final NamedParameterJdbcTemplate template; 있어야함
    //4) insert된 레코드에서 회원 번호를 추출
    Long product_id = ((BigDecimal)keyHolder.getKeys().get("member_id")).longValue();       //여기까지 작성하고 DAO Test만들어서 확인해보기

    return product_id;
  }
  //이메일 존재 유무
  @Override
  public boolean existMemberId(String email){
    String sql = "select count(email) from member where email = :email ";

    Map param = Map.of("email", email);
    //조회는 항상 단일행인지 단일열인지 생각해야한다. (이 경우는 단일열 => queryForObject 사용.)
    Integer cnt = template.queryForObject(sql, param, Integer.class); //단일열이니 단일타입으로 받을 수 있다.

    return cnt == 1 ? true : false; //cnt가 1이면 true, 아니면 false
  }

  @Override
  public Optional<Member> findByEmailAndPasswd(String email, String passwd) {
    StringBuffer sql = new StringBuffer();
    sql.append("select * from member ");
    sql.append(" where email = :email ");
    sql.append("   and passwd = :passwd ");


    Map param = Map.of("email", email, "passwd", passwd);

    try{                //단일 행, 다중 열
      Member member = template.queryForObject(sql.toString(), param, new BeanPropertyRowMapper<>(Member.class));
      return Optional.of(member);
    }catch(EmptyResultDataAccessException e){
      return Optional.empty();
    }
    //레코드를 받을 수 있는 자바객체가 필요 : new BeanPropertyRowMapper<>(Member.class) 멤버객체에 저장됨
  }
}




















