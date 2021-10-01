package zone.jiefei.service;

import io.swagger.models.auth.In;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zone.jiefei.entity.Students;

@FeignClient(value = "erc-students")
public interface IStudentsService {

    @GetMapping(value = "/api/students/getByCartId")
    Students getByCartId(@RequestParam("cartId")String cartId);

    @GetMapping(value = "/api/students/getById")
    Students getById(@RequestParam("id")Integer id);

    @PutMapping(value = "/api/students/updateWishesById")
    boolean updateWishesById(@RequestParam("id")Integer id, @RequestParam("wishes")Integer wishes);
}
