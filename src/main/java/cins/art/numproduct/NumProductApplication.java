package cins.art.numproduct;

import cins.art.numproduct.Util.thread.MyThread;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("cins.art.numproduct.mapper")
@EnableSwagger2
@EnableCaching
public class NumProductApplication {
	public static void main(String[] args) {
//		MyThread myThread = new MyThread();
//		myThread.start();
		SpringApplication.run(NumProductApplication.class, args);
	}
}
