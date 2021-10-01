package zone.jiefei.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import zone.jiefei.dto.*;
import zone.jiefei.entity.Students;
import zone.jiefei.mapper.StudentsMapper;
import zone.jiefei.service.IStudentsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
@Slf4j
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Students> implements IStudentsService {

    @Autowired
    private StudentsMapper studentsMapper;

    /**
     * fetch
     *
     * @param cardId 用户的身份证
     * @return 用户的基本信息
     * @Author zhanghaoqi
     * @Description 用户登录
     * @Data 18:16 2020/10/5
     */
    @Override
    public StudentDto login(String cardId ) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students student = studentsMapper.selectOne(queryWrapper);
        //判断学生是否存在
        if (student == null) {
            return null;
        }
        //判断学生是否报道
        StudentDto judgeStatusSure = StudentsUtils.judgeStatus(studentsMapper, cardId);
        if (judgeStatusSure != null) {
            return judgeStatusSure;
        }
        StudentDto studentDto = new StudentDto();
        BeanUtil.copyProperties(student, studentDto);
        return studentDto;

    }


    /**
     * fetch
     *
     * @param student 最新的学生信息
     * @return 学生的最新信息
     * @Author zhanghaoqi
     * @Description 用户的最新数据提交
     * @Data 19:28 2020/10/5
     */
    @Override
    public Map<Integer, StudentDto> submit( Students student ) {
        Map<Integer, StudentDto> map = new HashMap<>();
        Integer statusSure = 1;
        Integer unStatusSure = 0;
        //判断学生是否已经报道
        StudentDto judgeStatusSure = StudentsUtils.judgeStatus(studentsMapper, student.getCardId());
        if (judgeStatusSure != null) {
            map.put(statusSure, judgeStatusSure);
            return map;
        }
        //设置未报到学生的信息

        student.setStatus(statusSure);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", student.getCardId());
        studentsMapper.update(student, queryWrapper);
        Students studentMessage = studentsMapper.selectOne(queryWrapper);
        StudentDto studentDto = new StudentDto();
        BeanUtil.copyProperties(studentMessage, studentDto);
        map.put(unStatusSure, studentDto);
        return map;
    }


    /**
     * 根据学院/专业/班级简称 模糊查询信息数据
     *
     * @param type   传入 0 查询学院 传入 1 查询专业，传入 2 查询班级
     * @param key    关键字查询 输入学院/专业/班级关键字
     * @param status 是否报道
     * @param sex    性别
     */
    @Override
    public List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex ) {
        if (type != null && key != null && !key.equals("")) {
            key = "%" + key + "%";
            return studentsMapper.sqlStudentByCollegeAcademyClass(type, key, status, sex);
        }
        return null;
    }

    @Override
    public List<StudentInfosDto> getRankingList(Integer page, Integer size ) {
        IPage<Students> studentPage = new Page<>(page, size);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();

        queryWrapper.lambda().select(Students::getName, Students::getClasses, Students::getWishes).orderByDesc(Students::getWishes).orderByAsc(Students::getId);


        studentsMapper.selectPage(studentPage, queryWrapper);
        List<Students> records = studentPage.getRecords();
        return records.stream().map(record -> {
            StudentInfosDto studentInfos = new StudentInfosDto();
            BeanUtil.copyProperties(record, studentInfos);
            return studentInfos;
        }).collect(Collectors.toList());
    }


    /**
     * fetch
     *
     * @param sex     性别
     * @param type    0查询学院 1查询专业 2 查询班级
     * @param academy 学院名
     * @param batch   批次
     * @param batch   批次
     * @return java.util.List<com.marchsoft.api.dto.RegistDataDto>
     * @Author lixiangxiang
     * @Description 查询报到数据
     * @Data 17:48 2020/10/6
     */
    @Override
    public List<RegistDataDto> findRegisterData(Integer sex, Integer type, String academy, Integer batch, Integer postgraduate) {
        String sexStr = null;
        String typeStr = null;
        //判断性别
        if (sex == 0) {
            sexStr = "女";
        } else if (sex == 1) {
            sexStr = "男";
        }
        if (type != null) {
            if (type == 0) {
                typeStr = "academy";
            } else if (type == 1) {
                typeStr = "major_short_name";
            } else if (type == 2) {
                typeStr = "classes";
            }
        }
        List<RegistDataDto> registerData = studentsMapper.findRegisterData(sexStr, typeStr, academy, batch,postgraduate);
        List<RegistDataDto> academyData = new ArrayList<>();
        String [] academyNames={
                "生命科技学院","经济与管理学院","机电学院",
                "食品学院","动物科技学院","园艺园林学院",
                "资源与环境学院","信息工程学院","化学化工学院",
                "文法学院","教育科学学院","艺术学院","服装学院",
                "数学科学学院","外国语学院", "体育学院",
                "人工智能学院","国际教育学院"
        };
        if (type != null) {
            //学院按上面集合顺序排序
            if (type == 0) {
                for (int i = 0; i < academyNames.length; i++) {
                    for (RegistDataDto data : registerData) {
                        if (academyNames[i].equals(data.getName())) {
                            academyData.add(data);
                        }
                    }
                }
                return academyData;
            }
        }
        return registerData;
    }


    /**
     * @param key : 关键字
     * @return com.marchsoft.response.ResponseResult
     * @description 查找管理员本院已经报道没有注册人员
     * @author linlikang
     * @date 2020/10/5 21:51
     **/
    @Override
    public List<Students> selByKey( String key, String type ) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        queryWrapper.eq("conStatus", 0);
//        String type = admin.getType();
        queryWrapper.like("academy", type);
        if ((key != null)) {
            if (key.contains("-")) {
                key = key.replace("-", "#");
            }
                String finalKey = key;
                //使用and嵌套
                queryWrapper.and(Wrapper -> Wrapper.like("name", finalKey).or().like("phone_num", finalKey).or().like("classes", finalKey));
        }
        List<Students> list = list(queryWrapper);
        return list;
    }


    /**
     * @param cardId : 证件号
     * @return com.marchsoft.response.ResponseResult
     * @description 确认注册
     * @author linlikang
     * @date 2020/10/5 18:21
     **/
    @Override
    public int updStatus( String cardId ) {
        log.info("【需要更新的状态身份证号：】" + cardId);
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students students = new Students();
        students.setConStatus(1);
        students.setCreatedAt(LocalDateTime.now());
        ;

        //Students stu = studentsMapper.selectOne(queryWrapper);
        //stu.setCreatedAt(LocalDateTime.now());
        //stu.setConStatus(1);
        return studentsMapper.update(students, queryWrapper);
    }

    /**
     * @param cardId : 证件号
     * @return com.marchsoft.api.entity.Students
     * @description 按照证件号查找学生
     * @author linlikang
     * @date 2020/10/6 17:20
     **/
    @Override
    public Students getOneStudentByCardId( String cardId ) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("card_id", cardId);
        Students student = studentsMapper.selectOne(queryWrapper);
        return student;
    }


    /**
     * @param key  :关键字
     * @param type :管理员学院
     * @return com.marchso0ft.response.ResponseResult
     * @description
     * @author linlikang
     * @date 2020/10/5 21:50
     **/
    @Override
        public IPage<Students> selRegAllStu(String key, String type) {
        QueryWrapper<Students> queryWrapper = new QueryWrapper<>();
        //MTYwKMjExMTk2MjkxMQ==
        queryWrapper.like("academy", type);
        if (key != null) {
            if(key.contains("-")) {
                key = key.replace("-", "#");
            }
            if(key.equals("已报到")){
                queryWrapper.eq("conStatus",1);
            }
            else if(key.equals("未报到")){
                queryWrapper.eq("conStatus",0);
            }
            else{
                String finalKey = key;
                //使用and嵌套
                queryWrapper.and(Wrapper -> Wrapper.like("name", finalKey).or().like("phone_num", finalKey).or().like("classes", finalKey));
            }
        }
        Page<Students> page = new Page<>(0, 1000);
        return studentsMapper.selectPage(page, queryWrapper);


    }

    /**
     * fetch
     *
     * @param num 数据数量
     * @return java.util.List<com.marchsoft.api.dto.RecentRegisterDataDto>
     * @Author lixiangxiang
     * @Description 查找最近报到的num条数据
     * @Data 18:12 2020/10/6
     */

    @Override
    public List<RecentRegisterDataDto> findRecentData(int num ) {
        return studentsMapper.findRecentData(num);
    }

    @Override
    public Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum() {
        Map<Integer, List<TimeRegisterDataDto>> batchMap = new HashMap<>(16);
        int batchNum = 3;
        for (int i = 1; i <= batchNum; i++) {
            batchMap.put(i, studentsMapper.getTimeRegisterNum(i));
        }
        return batchMap;
    }

    @Override
    public List<AddressDataDto> getAddressAndLatAndLng( int second ) {
        return studentsMapper.getAddress(second);

    }

    @Override
    public List<AddressDataDto> getAllLatAndLng() {
        return  studentsMapper.getAllLatAndLng();
    }
}