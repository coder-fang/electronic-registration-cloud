package zone.jiefei.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zone.jiefei.entity.OpenId;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
public interface IOpenIdService extends IService<OpenId> {

    Integer selectCount(String openId);

}
