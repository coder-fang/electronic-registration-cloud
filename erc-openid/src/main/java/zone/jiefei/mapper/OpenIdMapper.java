package zone.jiefei.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import zone.jiefei.entity.OpenId;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */

@Component
@Repository
public interface OpenIdMapper extends BaseMapper<OpenId> {

}
