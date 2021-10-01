package zone.jiefei.service;



import com.baomidou.mybatisplus.extension.service.IService;
import zone.jiefei.entity.Admin;

import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
public interface IAdminService extends IService<Admin> {

    /**
     * fetch
     * @Author lixiangxiang
     * @Description
     * @Data 17:48 2020/10/6
     * @param account 账号
     * @param password 密码
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */

    Map<String,Object> login(String account, String password);
}
