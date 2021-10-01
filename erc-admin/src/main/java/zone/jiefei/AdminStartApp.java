package zone.jiefei;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Wangmingcan
 * @date 2021/7/15 20:20
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AdminStartApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminStartApp.class,args);
    }
}
