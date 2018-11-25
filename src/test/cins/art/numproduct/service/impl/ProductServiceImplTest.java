package cins.art.numproduct.service.impl;

import cins.art.numproduct.entity.Product;
import cins.art.numproduct.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ProductServiceImplTest {
    @Autowired
    private ProductService productService;

    @Test
    public void addProduct() {
//        Product product = new Product();
//        product.setPicAddress("0xf02255121dbdeae0065c03f8cc7ce4abafa58e6f3a4cbdcbf0dbe80c58f4a7a7");
//        product.setUserAddress("0x87cbe12fea1b9167542860255651a2cdf03b4190");
//        product.setName("e");
//        product.setPictureUrl("images/7.jpg");
//        product.setVideoUrl("/user/video");
//        product.setCreteTime("2016.2.25");
//        product.setUploadTime(new Date());
//        product.setWidth(60);
//        product.setHeight(70);
//        product.setAuthor("clf");
//        product.setPrice(BigDecimal.valueOf(888.00));
//        product.setProductStatus(1);
//        product.setCategory("抽象");
//        product.setStyle("清新");
//        product.setTheme("美好");
//        productService.addProduct(product);
    }

    @Test
    public void findByStyle() {
//        List<Product> productList = productService.findByStyle("清新");
//        log.info("productList:{}",productList.toString());
    }
//    @Test
//    public void findBySelections(){
//        List<Product> productList = productService.findBySelections(1,"0","10000",0,1000,null,null,null,null,1,0);
//        log.info("productList:{}",productList.toString());
//    }
//    @Test
//    public void findByCompareStr(){
//        List<Product> productList = productService.findByCompareStr("cl");
//        log.info("productList:{}",productList.toString());
//    }
}