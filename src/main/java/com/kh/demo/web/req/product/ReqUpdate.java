package com.kh.demo.web.req.product;

import lombok.Data;

@Data
public class ReqUpdate {

  private Long productId;
  private String pname;
  private Long quantity;
  private Long price;


}
