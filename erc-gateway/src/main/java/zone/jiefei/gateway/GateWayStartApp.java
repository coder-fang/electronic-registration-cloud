package zone.jiefei.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Wangmingcan
 * @date 2021/7/15 20:52
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GateWayStartApp {
    public static void main(String[] args) {
        SpringApplication.run(GateWayStartApp.class,args);
    }
}
