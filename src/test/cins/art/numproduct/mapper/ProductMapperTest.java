package cins.art.numproduct.mapper;

import cins.art.numproduct.Util.convert.Convert;
import cins.art.numproduct.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;
    @Test
    public void findNewProductPageByPrice() {
    }
    @Test
    public void test(){
        HashSet hashSet = new HashSet();
        hashSet.add(1);
        hashSet.add(2);
        hashSet.add(3);
        hashSet.add(1);
        System.out.println(hashSet.toString());
    }
}