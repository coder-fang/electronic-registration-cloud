package zone.jiefei.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import zone.jiefei.dto.StudentDto;
import zone.jiefei.entity.Students;
import zone.jiefei.mapper.StudentsMapper;

/**
 * @ClassName StudentsUtils
 * @Description Students的工具类
 * @Author zhanghaoqi
 * @Date 2020/10/05 20:44
 */
public class StudentsUtils {
    /**
     * fetch
     * @Author zhanghaoqi
     * @Description 判断学生是否报道
     * @Data 20:12 2020/10/5
     * @param cardId 当前报道者的id
     * @return 报道 返回当前的学生信息，未报道返回null
     */
    public static StudentDto judgeStatus(StudentsMapper studentsMapper, String cardId) {
        //确认报道   1
        Integer statusSure = 1;
        //查询条件
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students students = studentsMapper.selectOne(queryWrapper);
        Integer status = students.getStatus();
        if (status.equals(statusSure)) {
            StudentDto studentDto = new StudentDto();
            BeanUtil.copyProperties(students, studentDto);
            return studentDto;
        } else {
            return null;
        }
    }
}
