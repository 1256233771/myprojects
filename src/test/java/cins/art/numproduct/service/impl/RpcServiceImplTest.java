package cins.art.numproduct.service.impl;

import cins.art.numproduct.service.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class RpcServiceImplTest {

    @Autowired
    private RpcService rpcService;
    @Test
    public void test(){

    }
//    @Test
//    public void createUser() {
//        try {
//            String re = (String) rpcService.createUser("123456");
//            log.info("re:{}",re);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//    }
//    @Test
//    public void testMiner(){
//        MyThread myThread = new MyThread();
//        myThread.run();
//    }

}