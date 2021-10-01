package zone.jiefei.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import zone.jiefei.entity.Admin;
import org.springframework.stereotype.Service;
import zone.jiefei.mapper.AdminMapper;
import zone.jiefei.service.IAdminService;
import zone.jiefei.util.TokenUtil2;
import zone.jiefei.util.AdminUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
@Service
@RequiredArgsConstructor
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    private final TokenUtil2 tokenUtil2;

    @Override
    public Map<String,Object> login(String account, String password) {
        LambdaQueryWrapper<Admin> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getAccount, account).eq(Admin::getPassword, password);
        Admin admin = getOne(queryWrapper);
        if (ObjectUtil.isNotNull(admin)) {
            String token = admin.getToken();
//            检查token如果为空、或token登录时间已经超时
            if (StrUtil.isEmpty(token) || tokenUtil2.isExpire(token)) {
//                重新创建token。或者刷新登录时间
                token = tokenUtil2.createToken();
                admin.setToken(token);
                saveOrUpdate(admin);
            }
            AdminUtils.setLoginUser(admin);
            Map<String,Object> map = new HashMap<>(16);
            map.put("status",admin.getStatus());
            map.put("type",admin.getType());
            map.put("token",token);
            return map;
        }
        return null;
    }
}
