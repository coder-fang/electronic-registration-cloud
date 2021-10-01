package zone.jiefei;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Wangmingcan
 * @date 2021/7/16 23:18
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class DataStartApp {
    public static void main(String[] args) {
        SpringApplication.run(DataStartApp.class,args);
    }

}
