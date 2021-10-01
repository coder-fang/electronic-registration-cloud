package zone.jiefei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * @author Wangmingcan
 * @date 2021/8/9 20:23
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableAuthorizationServer
public class UaaStartApp {
    public static void main(String[] args) {
        SpringApplication.run(UaaStartApp.class,args);
    }
}
