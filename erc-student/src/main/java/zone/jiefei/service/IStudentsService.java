package zone.jiefei.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import zone.jiefei.dto.*;
import zone.jiefei.entity.Students;

import java.util.List;
import java.util.Map;

/**
 * <
 *  服务类
 * <p>
 * 服务类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
public interface IStudentsService extends IService<Students> {

    /**
     * fetch
     * @Author zhanghaoqi
     * @Description 学生登录
     * @Data 19:40 2020/10/5
     * @param cardId 身份证号
     * @return 当前用户的基本信息
     */
    StudentDto login(String cardId);




    /**
     * fetch
     * @Author zhanghaoqi
     * @Description 用户的最新数据提交
     * @Data 19:28 2020/10/5
     * @param student 最新的学生信息
     * @return 最新信息集合
     */
    Map<Integer,StudentDto> submit(Students student);


    /**
     * @Author lixiangxiang
     * @Description 查询报到数据
     * @Data 17:48 2020/10/6
     * @param sexStr 性别
     * @param type 0查询学院 1查询专业 2 查询班级
     * @param batch
     * @param academy 学院名
     * @return java.util.List<com.marchsoft.api.dto.RegistDataDto>
     */

    List<RegistDataDto> findRegisterData(Integer sexStr, Integer type, String s, Integer batch, Integer academy);

    List<Students> selByKey(String key, String type);

    Students getOneStudentByCardId(String cardId);

    int updStatus(String card_id);

    List<StudentInfosDto> getRankingList(Integer page, Integer size);

    IPage<Students> selRegAllStu(String key, String type);

    List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex);
    /**
     * fetch
     * @Author lixiangxiang
     * @Description
     * @Data 21:32 2020/10/6
     * @param num 获取数据数量
     * @return java.util.List<com.marchsoft.api.dto.RecentRegisterDataDto>
     */
    List<RecentRegisterDataDto> findRecentData(int num);
    /**
     * fetch
     * @Author lixiangxiang
     * @Description
     * @Data 21:32 2020/10/6
     * @return java.util.Map<java.lang.Integer,java.util.List<com.marchsoft.api.dto.TimeRegisterDataDto>>
     */
    Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum();
    /**
     * fetch
     * @Author lixiangxiang
     * @Description
     * @Data 23:15 2020/10/6
     * @param second 间隔时间
     * @return java.util.List<com.marchsoft.api.dto.AddressDataDto>
     */

    List<AddressDataDto> getAddressAndLatAndLng(int second);

    /**
     * fetch
     * @Author zhangpeitao
     * @Description 获取用户经纬度
     * @Data 23:34 2020/10/9
     * @return java.util.List<com.marchsoft.api.dto.AddressDataDto>
     */
    List<AddressDataDto> getAllLatAndLng();
}
