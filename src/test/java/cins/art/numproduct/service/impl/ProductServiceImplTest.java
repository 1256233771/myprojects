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

//    @Test
//    public void addProduct() {
//        Product product = new Product();
//        product.setPicAddress("1212114568");
//        product.setUserAddress("1212121212");
//        product.setName("sjks");
//        product.setPictureUrl("/user/hsud");
//        product.setVideoUrl("/user/video");
//        product.setCreteTime("2016.2.25");
//        product.setUploadTime(new Date());
//        product.setWidth(20);
//        product.setHeight(20);
//        product.setAuthor("clf");
//        product.setPrice(BigDecimal.valueOf(520.00));
//        product.setProductStatus(0);
//        product.setCategory("抽象");
//        product.setStyle("清新");
//        product.setTheme("美好");
//        productService.addProduct(product);
//    }

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