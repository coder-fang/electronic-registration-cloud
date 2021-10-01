package zone.jiefei.controller;


import cn.hutool.core.codec.Base64;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import zone.jiefei.common.ext.Access;
import zone.jiefei.common.ext.AccessLevel;
import zone.jiefei.dto.*;
import zone.jiefei.entity.Students;
import zone.jiefei.pojo.Admin;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;
import zone.jiefei.service.IStudentsService;
import zone.jiefei.utils.AdminUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
@RestController
@RequestMapping("/api/students")
@Api(tags = "学生模块")
@Slf4j
@RefreshScope
public class StudentsController {

    @Autowired
    private IStudentsService studentsService;

    @Value("${code.expirationTime}")
    private Integer expirationTime;


    @GetMapping("/getRankingList")
    @ApiOperation(value = "分页倒叙查询", notes = " \n author：Zhangpeitao")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer"),
            @ApiImplicitParam(name = "size", value = "页大小", dataType = "Integer")
    })
    public ResponseResult getRankingList(Integer page, Integer size ) {
        List<StudentInfosDto> studentInfosList = studentsService.getRankingList(page, size);
        return ResponseResult.ok(studentInfosList);
    }

    @GetMapping("/getWishCount")
    @ApiOperation(value = "返回祝福对象的祝福数", notes = " \n author：Zhangpeitao")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cartId", value = "祝福对象的身份证号")
    })
    public ResponseResult getWishCount( String cartId ) {
        QueryWrapper<Students> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.lambda().eq(Students::getCardId, cartId).select(Students::getWishes);
        Students one = studentsService.getOne(studentQueryWrapper);
        int wishes = 0;
        if (one != null) {
            wishes = one.getWishes();
        }
        return ResponseResult.ok(wishes);
    }
    @PostMapping("/login")
    @ApiOperation(value = "学生登录", notes = " \n author：zhanghaoqi")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")
    })
    public ResponseResult<StudentDto> login(@RequestParam("cardId") String cardId ) {
        log.info("【用户登录：】" + cardId);
        StudentDto login = studentsService.login(cardId);
        //判断当前用户是否注册
        if (login == null) {
            return ResponseResult.ok(ResponseEnum.UNKONW_IDCARD);
        } else if (login.getStatus() == 1) {
            return ResponseResult.ok(ResponseEnum.SYDYE_OK, login);
        } else if (login.getStatus() == 0) {
            return ResponseResult.ok(ResponseEnum.SYSUC_OF, login);
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }
    @PostMapping("/submit")
    @ApiOperation(value = "提交最新的学生信息", notes = " \n author：zhanghaoqi")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "student", value = "最新的学生信息")
    })
    public ResponseResult<StudentDto> submit( Students student ) {
        log.info("【用户提交：】" + student);

        Map<Integer, StudentDto> studentDtoMap = studentsService.submit(student);
        //判断该用户是否注册
        for (Integer key : studentDtoMap.keySet()) {
            if (key == 0) {
                return ResponseResult.ok(ResponseEnum.SYSUC_OF, studentDtoMap.get(0));
            }
            if (key == 1) {
                return ResponseResult.ok(ResponseEnum.SYDYE_OK, studentDtoMap.get(1));
            }
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }


    /**
     * fetch
     *
     * @param cardId 身份证号
     * @return com.marchsoft.response.ResponseResult<com.marchsoft.api.entity.Students>
     * @Author lixiangxiang
     * @Description 重置信息
     * @Data 0:02 2020/10/7
     */
    @ApiOperation(value = "重置信息", notes = "\n author:lixiangxiang")
    @GetMapping("/resetInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")})
    public ResponseResult<Students> resetInfo( @RequestParam(name = "cardId") String cardId ) {
        if (cardId != null) {
            //重置为初始值
            Students students = new Students();
            students.setStatus(0);
            students.setConStatus(0);
            students.setReligiousBelief(0);
            students.setFamilyNum("");
            students.setFamilyNumtwo("");
            students.setWechatId("");
            students.setQqId("");
            students.setAddress("");
            students.setPolApp("");
            students.setPhoneNum("");
            UpdateWrapper<Students> wrapper = new UpdateWrapper<>();
            wrapper.eq("card_id", cardId);
            boolean is = studentsService.update(students, wrapper);
            if(!is){
                return ResponseResult.error(ResponseEnum.CARD_ERROR);
            }
        } else {
            return ResponseResult.error(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        return ResponseResult.ok();
    }


    /**
     * status == 2
     * 确认注册权限
     * 查询本学院所有未注册学生
     *
     * @param key 关键字
     * @return com.marchsoft.response.ResponseResult
     * @author linlikang
     * @data 2020/9/27
     */
    @GetMapping("/conRegist")
    @ApiImplicitParam(name = "key", value = "条件查询：班级、姓名、电话")
    @ApiOperation(value = "查询已经报道，正在等待管理员确认注册的本院人员", notes = " \n author: linlikang")
    public ResponseResult<Object> conRegist( String key ) {

        log.info("【未注册学生查找关键字】" + key);
        if (!(AdminUtils.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtils.isAdmin();
        }
        Admin admin = (Admin) AdminUtils.isAdmin();
        log.info("admin = " + admin);
        List<Students> list = studentsService.selByKey(key, admin.getType());
        if (list.size() != 0) {
            return ResponseResult.ok(list);
        }
        return ResponseResult.error(ResponseEnum.NO_STUDENT);
    }

    /**
     * @author linlikang
     * @data 2020/9/27
     * @return com.marchsoft.response.ResponseResult
     * params：cardId(身份证号)
     */

    @ApiOperation(value = "确认注册，修改conStatus", notes = " \n author: linlikang")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cardId", value = "身份证号")
    })
    @PostMapping("/updRegist")
    public ResponseResult<Object> updRegist(@RequestParam(required = true,value = "cardId") String cardId) {
        //MTYwbMjA3MzQwOTI3Ng==   411381200110026577
        log.info("【确认注册】" + cardId);
        if (!(AdminUtils.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtils.isAdmin();
        }
        Admin admin = (Admin) AdminUtils.isAdmin();
        if (cardId != null) {
            Students stu = studentsService.getOneStudentByCardId(cardId);
            log.info("当前管理员管理的院系为：】" + admin.getType() + "【当前学生的院系为：】" + stu.getAcademy());
            if (stu.getAcademy().equals(admin.getType())) {
                if (stu.getConStatus() == 1) {
                    return ResponseResult.error(ResponseEnum.ALREADY_REGISTER);
                }
                int b = studentsService.updStatus(cardId);
                if (b == 1) {
                    return ResponseResult.ok(ResponseEnum.SUCCESS);
                }
                return ResponseResult.error(ResponseEnum.Exec_FAILTER);
            }
            return ResponseResult.error(ResponseEnum.INSUFFICIENT_PRIVILEGEX);
        }
        return ResponseResult.error(ResponseEnum.Exec_FAILTER);
    }

    /**
     * @return com.marchsoft.response.ResponseResult
     * @author linlikang
     * @data 2020/9/27
     * @params key 关键字，currentPage 当前页
     */
    @GetMapping("/selRegAllStu")
    @ResponseBody
    @ApiOperation(value = "人员检索查询管理员所在学院所有新生", notes = " \n author: linlikang")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "条件查询：姓名、手机号、宿舍号、已报到（未报到）"),
    })
    public ResponseResult<Object> selRegAllStu(String key) {
        log.info("【查看学院所有学生】"+key);
        if (!(AdminUtils.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtils.isAdmin();
        }
        Admin admin = (Admin) AdminUtils.isAdmin();
        IPage<Students> studentsIPage =  studentsService.selRegAllStu(key,admin.getType());
        if (studentsIPage.getSize() != 0) {
            return ResponseResult.ok(studentsIPage);
        }
        return ResponseResult.ok("查询数据为0");
    }

    /**
     * 二维码扫描返回该学生数据
     *
     * @param name 时间戳+证件号
     * @return ResponseResult
     * @author linlikang
     * @author linlikang
     */
    @Access(level = AccessLevel.LOGIN)
    @ResponseBody
    @GetMapping("/getStudentById")
    @ApiOperation(value = "管理员扫码回显学生信息", notes = " \n author: linlikang")
    @ApiImplicitParam(name = "name", value = "加密的card_id")
    public ResponseResult<Object> getStudentById(@RequestParam(value = "name") String name ) {
        log.info("【回显信息】" + name);
        if (!(AdminUtils.isAdmin() instanceof Admin)) {
            return (ResponseResult<Object>) AdminUtils.isAdmin();
        }
        Admin admin = (Admin) AdminUtils.isAdmin();
        String decodeName = Base64.decodeStr(name.substring(0, 4) + name.substring(5));
        //3. 分割时间戳和id
        String[] trueName = decodeName.split("&",2);
//        如果name中&字符丢失
        if(trueName.length != 2){
            return ResponseResult.error(ResponseEnum.ILLEGAL_ARGUMENT);
        }
        Students student = studentsService.getOneStudentByCardId(trueName[1]);

        if (student == null) {
            return ResponseResult.ok(ResponseEnum.NO_STUDENT);
        }
        //如果学院不同，
        else if (!student.getAcademy().equals(admin.getType())) {
            return ResponseResult.error(ResponseEnum.INSUFFICIENT_PRIVILEGEX);
        }
        //判断当前学生是否已经注册
        else if (student.getConStatus() == 1) {
            return ResponseResult.ok(student);
        }
        //判断二维码是否失效
        else if (System.currentTimeMillis() - Long.parseLong(trueName[0]) > expirationTime) {
            return ResponseResult.error(ResponseEnum.QRCODE_FAIL);
        } else {
            return ResponseResult.ok(student);
        }

    }

    @GetMapping(value = "/getByCartId")
    public Students getByCartId(String cartId) {
        log.info("远程服务调用getByCartId:"+cartId);
        QueryWrapper<Students> studentsQueryWrapper = new QueryWrapper<>();
        studentsQueryWrapper.lambda().eq(Students::getCardId, cartId);
        return studentsService.getOne(studentsQueryWrapper);
    }

    @GetMapping(value = "/getById")
    Students getById(Integer id) {
        log.info("远程服务调用getById:"+id);
        return studentsService.getById(id);
    }

    @PutMapping(value = "/updateWishesById")
    boolean updateWishesById(Integer id, Integer wishes) {
        log.info("远程服务调用updateWishesById:"+id);
        Students student = new Students();
        student.setId(id);
        student.setWishes(wishes);
        return studentsService.updateById(student);
    }

    @GetMapping("/findRegisterData")
    List<RegistDataDto> findRegisterData(@RequestParam(name = "sex",required = false) Integer sex,
                                         @RequestParam(name = "type", required = false) Integer type,
                                         @RequestParam(name = "academy", required = false) String academy,
                                         @RequestParam(name = "batch", required = false) Integer batch,
                                         @RequestParam(name = "postgraduate",defaultValue = "0") Integer postgraduate){
        log.info("远程过程调用findRegisterData");
        return studentsService.findRegisterData(sex, type, academy, batch,postgraduate);
    }

    @GetMapping("/showStudentByCollegeAcademyClass")
    List<StudentProfileDto> showStudentByCollegeAcademyClass(Integer type, String key, Integer status, Integer sex) {
        log.info("远程过程调用showStudentByCollegeAcademyClass");
        return studentsService.showStudentByCollegeAcademyClass(type, key, status, sex);
    }

    @GetMapping("/findRecentData")
    List<RecentRegisterDataDto> findRecentData(@RequestParam(name = "num",defaultValue = "0")int num) {
        log.info("远程过程调用findRecentData");
        return studentsService.findRecentData(num);
    }

    @GetMapping("/getTimeRegisterNum")
    Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum() {
        log.info("远程过程调用getTimeRegisterNum");
        return studentsService.getTimeRegisterNum();
    }

    @GetMapping("/getAddressAndLatAndLng")
    List<AddressDataDto> getAddressAndLatAndLng(@RequestParam(defaultValue = "5") int second) {
        log.info("远程过程调用getAddressAndLatAndLng");
        return studentsService.getAddressAndLatAndLng(second);
    }

    @GetMapping("/getAllLatAndLng")
    List<AddressDataDto> getAllLatAndLng() {
        log.info("远程过程调用getAllLatAndLng");
        return studentsService.getAllLatAndLng();
    }
}





























