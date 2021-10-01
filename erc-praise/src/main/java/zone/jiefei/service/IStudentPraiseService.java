package zone.jiefei.service;


import com.baomidou.mybatisplus.extension.service.IService;
import zone.jiefei.entity.StudentPraise;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
public interface IStudentPraiseService extends IService<StudentPraise> {

    Boolean clickWish(String id, String openId);
}
