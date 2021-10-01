package zone.jiefei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author Wangmingcan
 * @date 2021/7/16 21:44
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
//@EnableResourceServer
public class StudentsStartApp {
    public static void main(String[] args) {
        SpringApplication.run(StudentsStartApp.class,args);
    }
}
