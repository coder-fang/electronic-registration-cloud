package zone.jiefei.controller;


import lombok.RequiredArgsConstructor;
import zone.jiefei.common.ext.Access;
import zone.jiefei.common.ext.AccessLevel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.jiefei.entity.Admin;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;
import zone.jiefei.service.IAdminService;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 * </p>
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@Api(tags = "管理员模块")
public class AdminController {

    private final IAdminService adminService;
    
    @GetMapping("/login")
    @ApiOperation(value = "登录", notes = " \n author：Zhangpeitao")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号"),
            @ApiImplicitParam(name = "password", value = "密码")
    })
    @Access(level = AccessLevel.ALL)
    public ResponseResult<Object> login(String account, String password ) {
        Map<String, Object> tokenAndStatus = adminService.login(account, password);
//        首先判断用户是否登陆过，如果登陆过，则对应token会存在
//        如果查询为空，返回错误，提示重新登录
        if (tokenAndStatus == null || tokenAndStatus.size() == 0) {
            return ResponseResult.error(ResponseEnum.LOGIN_IS_FAIL);
        }
        return ResponseResult.ok(tokenAndStatus);
    }

    @GetMapping("/test")
    @Access(level = AccessLevel.ALL)
    public String test() {
        Admin admin = adminService.getById(1);
        return admin.getAccount();
    }

}

