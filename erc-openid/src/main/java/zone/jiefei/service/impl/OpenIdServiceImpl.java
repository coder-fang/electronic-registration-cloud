package zone.jiefei.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zone.jiefei.entity.OpenId;
import zone.jiefei.mapper.OpenIdMapper;
import zone.jiefei.service.IOpenIdService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OpenIdServiceImpl extends ServiceImpl<OpenIdMapper, OpenId> implements IOpenIdService {

    private final OpenIdMapper openIdMapper;

    @Override
    public Integer selectCount(String openId) {
        log.info("远程服务调用selectCount:"+openId);
        QueryWrapper<OpenId> openIdQueryWrapper = new QueryWrapper<>();
        openIdQueryWrapper.lambda().eq(OpenId::getOpenid, openId);
        return openIdMapper.selectCount(openIdQueryWrapper);
    }
}
