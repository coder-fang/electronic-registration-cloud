package zone.jiefei.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import zone.jiefei.entity.StudentPraise;
import zone.jiefei.entity.Students;
import zone.jiefei.mapper.StudentPraiseMapper;
import zone.jiefei.service.IOpenIdService;
import zone.jiefei.service.IStudentPraiseService;
import zone.jiefei.service.IStudentsService;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class StudentPraiseServiceImpl extends ServiceImpl<StudentPraiseMapper, StudentPraise> implements IStudentPraiseService {

    private final StudentPraiseMapper studentPraiseMapper;

    private final IStudentsService studentsService;

    private final IOpenIdService openIdService;

    /**
     * fetch
     *
     * @param cartId 学生身份证号
     * @param openId 微信用户id
     * @return java.lang.Boolean
     * @Author zhangpeitao
     * @Description
     * @Data 11:49 2020/8/1
     */
    @Override
    @GlobalTransactional
    public Boolean clickWish( String cartId, String openId ) {
        log.info("【学生身份证为：】" + cartId + "【当前用户的openid为】" + openId);
        Integer openIdCount = openIdService.selectCount(openId);
        if (openIdCount != 0) {
            Students students = studentsService.getByCartId(cartId);
            Integer id = students.getId();
            //将openid和考生id插入
            QueryWrapper<StudentPraise> studentPraiseWrapper = new QueryWrapper<>();
            studentPraiseWrapper.lambda().eq(StudentPraise::getStudentId, id).eq(StudentPraise::getOpenid, openId);
            Integer count = studentPraiseMapper.selectCount(studentPraiseWrapper);
            if (count == 0) {
                StudentPraise studentPraise = new StudentPraise();
                studentPraise.setStudentId(id);
                studentPraise.setOpenid(openId);
                studentPraiseMapper.insert(studentPraise);
                log.info("【插入数据成功】");
                //查询当前的祝福数量
                Students selectOne = studentsService.getById(id);
                int wishes = 0;
                if (ObjectUtil.isNotNull(selectOne)) {
                    if (ObjectUtil.isNotNull(selectOne.getWishes())) {
                        wishes = selectOne.getWishes();
                    }
                }
                log.info("【当前的祝福数量为：】" + wishes);
                //更新库中的祝福数量
                return studentsService.updateWishesById(id, wishes+1);
            } else {
                return false;
            }
        } else {
            return null;
        }
    }
}
