package zone.jiefei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wangmingcan
 * @date 2021/7/16 21:44
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class Students2StartApp {
    public static void main(String[] args) {
        SpringApplication.run(Students2StartApp.class,args);
    }
}
