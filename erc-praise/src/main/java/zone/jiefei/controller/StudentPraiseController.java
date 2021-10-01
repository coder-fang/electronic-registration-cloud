package zone.jiefei.controller;


import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;
import zone.jiefei.service.IStudentPraiseService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
@RestController
@RequestMapping("/api/studentPraise")
public class StudentPraiseController {

    @Autowired
    private IStudentPraiseService studentPraiseServicer;


    @GetMapping("/clickWish")
    @ApiOperation(value = "点击祝福", notes = " \n author：Zhangpeitao")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "身份证id", dataType = "String"),
            @ApiImplicitParam(name = "openId", value = "用户openid", dataType = "String")
    })
    public ResponseResult clickWish(String cartId, String openId ) {
        Boolean clickWish = studentPraiseServicer.clickWish(cartId, openId);
        if (clickWish == null) {
            return ResponseResult.ok(ResponseEnum.NOOPENID);
        } else if (clickWish) {
            return ResponseResult.ok();
        } else {
            return ResponseResult.ok(ResponseEnum.HASWISHED);
        }

    }

}

