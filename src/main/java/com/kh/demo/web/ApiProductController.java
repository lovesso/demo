package com.kh.demo.web;

import com.kh.demo.domain.entity.Product;
import com.kh.demo.domain.product.svc.ProductSVC;
import com.kh.demo.web.api.ApiResponse;
import com.kh.demo.web.req.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
//@Controller
@RestController //@Controller + @ResponseBody
@RequiredArgsConstructor //
@RequestMapping("/api/products")
public class ApiProductController {

  private final ProductSVC productSVC; //서비스객체 생성

  //등록 --등록 잘됐는지 안됐는지 응답
  //@ResponseBody
  @PostMapping  // GET  http://localhost:9080/api/products
  public ApiResponse<?> add(@RequestBody ReqSave reqSave) { //@Data를 했기때문에 ReqSave 내부적으로 호출되어 값이 찍히게 된다.
    log.info("reqSave={}", reqSave);  //값이 제대로 넘어오는지 확인  //@RequestBody 요청메세지 바디에 제이슨포맷 문자열을 자바객체로 매핑한다.

    //1) 유효성 검증

    //2) 상품등록처리
    Product product = new Product(); //프로덕트 객체생성.
//    product.setPname(reqSave.getPname()); // 두 객체의 필드가 같으면 줄일수 있다. BeanUtils 사용
//    product.setQuantity(reqSave.getQuantity());
//    product.setProductId(reqSave.getPrice());

    BeanUtils.copyProperties(reqSave, product); //reqSave를 product객체에다가 복사
//    productSVC.save(product); //DAO에 저장됨.
    Long productId = productSVC.save(product);

    ResSave resSave = new ResSave(productId, reqSave.getPname());

    String rtDetail = "상품번호 " + productId + " 가 등록 되었습니다";
    ApiResponse<ResSave> res = ApiResponse.createApiResponseDetail(
        ResCode.OK.getCode(), ResCode.OK.name(), rtDetail, resSave);
    return res;
  }


  //조회 -- 상품번호에 대한 정보를 응답
  //@ResponseBody
  @GetMapping("/{pid}")
  public ApiResponse<ResFindById> findById(@PathVariable("pid") Long pid) {
    log.info("pid={}", pid);
    Optional<Product> optionalProduct = productSVC.findById(pid);

    ApiResponse<ResFindById> res = null;

    //상품을 찾은 경우
    if (optionalProduct.isPresent()) {
      Product findedProduct = optionalProduct.get();

      ResFindById resFindById = new ResFindById();
      BeanUtils.copyProperties(findedProduct, resFindById);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resFindById);

      //상품을 못찾은경우

    } else {
      String rtDetail = "상품번호 : " + pid + " 찾고자하는 상품정보가 없습니다.";
      res = ApiResponse.createApiResponseDetail(ResCode.FAIL.getCode(), ResCode.FAIL.name(), rtDetail, null);
    }
    return res;

  }

  //수정 --수정이 잘됐는지 안됐는지 응답해주기
  //@ResponseBody
  @PatchMapping("/{pid}")
  public ApiResponse<?> update(
      @PathVariable("pid") Long pid,
      @RequestBody ReqUpdate reqUpdate
  ) {
    log.info("pid={}", pid);
    log.info("reqUpdate={}", reqUpdate);

    //1) 유효성체크

    //2)수정
    Product product = new Product();
    BeanUtils.copyProperties(reqUpdate, product);

    int updatedCnt = productSVC.updateById(pid, product);
    ApiResponse<ResUpdate> res = null;
    if (updatedCnt == 1) {
      ResUpdate resUpdate = new ResUpdate();
      BeanUtils.copyProperties(reqUpdate, resUpdate);
      resUpdate.setProductId(pid);

      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), resUpdate);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }

    return res;
  }

  //삭제 --삭제 잘 되었는지 응답
  //@ResponseBody
  @DeleteMapping("/{pid}")
  public ApiResponse<?> delete(@PathVariable("pid") Long pid) {

    int deletedCnt = productSVC.deleteById(pid);
    ApiResponse<ResUpdate> res = null;

    if (deletedCnt == 1) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), null);
    } else {
      res = ApiResponse.createApiResponse(ResCode.FAIL.getCode(), ResCode.FAIL.name(), null);
    }
    return res;
  }

  //목록 -- 상품 목록이 있으면 보내주고 없으면 없다고 응답
  // @ResponseBody
  @GetMapping
  public ApiResponse<?> list() {


    try {
      Thread.sleep(3000); //3초 지연
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }


    List<Product> list = productSVC.findAll();

    ApiResponse<List<Product>> res = null;
    if (list.size() > 0) {
      res = ApiResponse.createApiResponse(ResCode.OK.getCode(), ResCode.OK.name(), list);
      res.setTotalCnt(productSVC.totalCnt());
    } else {
      res = ApiResponse.createApiResponseDetail(
          ResCode.OK.getCode(), ResCode.OK.name(), "등록된 상품이 한 건도 없습니다.", list);
      res.setTotalCnt(list.size());
    }
    return res;
  }
}

