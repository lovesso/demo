package com.kh.demo.web.form.product;

import lombok.Data;

@Data
public class AddForm { //SSR -> 서버사이드랜더링
  private String pname;
  private Long quantity;
  private Long price;
}
