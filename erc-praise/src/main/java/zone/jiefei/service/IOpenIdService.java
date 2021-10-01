package zone.jiefei.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Wangmingcan
 * @date 2021/7/16 21:59
 * @description
 */
@FeignClient(value = "erc-openid")
public interface IOpenIdService {

    @GetMapping(value = "/api/login/selectCount")
    Integer selectCount(@RequestParam("openId")String openId);

}
