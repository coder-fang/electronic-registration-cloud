package zone.jiefei.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zone.jiefei.entity.OpenId;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;
import zone.jiefei.service.IOpenIdService;
import zone.jiefei.util.WeiXinUtil;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author zhangpeitao
 * @Date 2020/8/18:40
 * @VERSION 1.0
 */
@Api(tags = "微信登录")
@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired

    private WeiXinUtil weiXinUtil;

    @Autowired
    private IOpenIdService openIdService;


    @GetMapping("/weiXinLogin")
    public ResponseResult weiXinLogin(String code ) {
        String openId = weiXinUtil.getOpenId(code);
        if (openId == null) {
            return ResponseResult.ok(ResponseEnum.CODEISFAIL);
        }

        QueryWrapper<OpenId> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OpenId> openIdLambdaQueryWrapper = queryWrapper.lambda().eq(OpenId::getOpenid, openId);
        int count = openIdService.count(openIdLambdaQueryWrapper);
        if (count == 0) {
            OpenId openIdEntity = new OpenId();
            openIdEntity.setOpenid(openId);
            openIdService.save(openIdEntity);
        }
        return ResponseResult.ok(openId);
    }

    @GetMapping(value = "/selectCount")
    public Integer selectCount(String openId){
        return openIdService.selectCount(openId);
    }
}
