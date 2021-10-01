package zone.jiefei.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import zone.jiefei.dto.*;
import zone.jiefei.response.ResponseResult;
import zone.jiefei.response.enums.ResponseEnum;
import zone.jiefei.service.IStudentsService;

import java.util.List;
import java.util.Map;

/**
 * fetch
 * @Author lixiangxiang
 * @Version 1.0
 */
@RestController
@Api(tags = "报到数据")
@RequestMapping("/api/ScreenData")
@RequiredArgsConstructor
public class DataController {


    private final IStudentsService studentsService;

    @ApiOperation(value = "获取报到数据", notes = "\n author: LiXiangXiang")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "academy",
                    value = "非必须传入参数，\n " +
                            "如果为学校端登录时不用传入参数\n" +
                            "传入参数后会查询对应学院下的数据\n" +
                            "（例如若参数type为1 name为化学化工学院 sex为1 则查询的是化学化工学院下的所有专业男生报到数据）"),
            @ApiImplicitParam(name = "sex",
                    value = "必须传入参数，\n " +
                            "传入 0 获取女生报到数据，\n " +
                            "传入 1 获得男生报到数据，\n" +
                            "传入 2 获得所有人报到数据\n"),
            @ApiImplicitParam(name = "type",
                    value = "非必须传入参数，\n " +
                            "不传入获取全校报到数据\n" +
                            "传入 0 获得各学院报到数据，\n " +
                            "传入 1 获得专业报到数据，\n" +
                            "传入 2 获得班级报到数据，\n"
            ),
            @ApiImplicitParam(name = "postgraduate",
                    value = "默认为0，\n " +
                            "传入0为本科生数据\n" +
                            "传入1为研究生数据\n "
            ),
            @ApiImplicitParam(name = "batch",
                    value = "非必须传入参数，\n " +
                            "不传入数据获取全部批次\n" +
                            "传入 n 获得第n批，\n "
            ),

    })
    @GetMapping("/getMajorInfo")
    public ResponseResult<List<RegistDataDto>> getRegisterData(
            @RequestParam(name = "sex",required = false) Integer sex,
            @RequestParam(name = "type", required = false) Integer type,
            @RequestParam(name = "academy", required = false) String academy,
            @RequestParam(name = "batch", required = false) Integer batch,
            @RequestParam(name = "postgraduate",defaultValue = "0") Integer postgraduate) {
             ResponseResult<List<RegistDataDto>> result;
             int sexNum  = 2;
             List<RegistDataDto> registerData ;
                if (sex != null && sex <= sexNum && sex >= 0) {
                    registerData = studentsService.findRegisterData(sex, type, academy, batch,postgraduate);
                    if (registerData != null) {
                        result = ResponseResult.ok(registerData);
                    } else {
                        result = ResponseResult.error(ResponseEnum.SEVER_ERROR);
                    }
                } else {
                    result = ResponseResult.error(ResponseEnum.ILLEGAL_ARGUMENT);
                }
        return result;
    }
    /**
    * @description 获取未报到或已报道学生信息
    * @author      linlikang
    * @date        2020/10/6 9:12
    **/
    @ApiOperation(value = "获取未报到或已报到学生信息", notes = "\n author: LiXiangXiang")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key",
                    value = "必须传入参数，\n " +
                            "搜索关键字，由前端传入需要搜索的学院/专业/班级 简称 进行模糊查询"),
            @ApiImplicitParam(name = "sex",
                    value = "必须传入参数，\n " +
                            "传入 0 查询女生，\n " +
                            "传入 1 查询男生，\n" +
                            "传入 2 查询所有\n"),
            @ApiImplicitParam(name = "type",
                    value = "必须传入参数，\n " +
                            "传入 0 查询学院，\n " +
                            "传入 1 查询专业，\n" +
                            "传入 2 查询班级，\n"
            ),
            @ApiImplicitParam(name = "status",
                    value = "必须传入参数，\n " +
                            "0查询全部\n" +
                            "1查询报道人，\n "+
                            "2查询未道人，\n "
            ),

    })
    @GetMapping("/showStuByCAC")
    public ResponseResult showStudentByCollegeAcademyClass(
            @RequestParam(name = "type",required = false)Integer type,
            @RequestParam(name = "key",required = false)String key,
            @RequestParam(name = "status",required = false)Integer status,
            @RequestParam(name = "sex",required = false)Integer sex ) {
        type = type == null ? 2 : type;
        if (key != null && !"".equals(key)) {
            if (status == null) {
                status = 0;
            }
            if (sex == null) {
                sex = 0;
            }
            List<StudentProfileDto> list = studentsService.showStudentByCollegeAcademyClass(type, key, status, sex);
            if (list != null) {
               return ResponseResult.ok(ResponseEnum.SUCCESS,list);
            }
            return ResponseResult.error(ResponseEnum.ERROR);
        }
        return ResponseResult.error((ResponseEnum.ILLEGAL_ARGUMENT));
    }

    @GetMapping("/recentData")
    @ApiOperation(value = "获取最近num条注册完成数据",notes = " \n author：lixiangxiang")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "num",value = "获取数据量")
    })
    public ResponseResult<List<RecentRegisterDataDto>> getRecentData(@RequestParam(name = "num",defaultValue = "0") int num ){
        List<RecentRegisterDataDto> studentsServiceRecentData = studentsService.findRecentData(num);
        return ResponseResult.ok(studentsServiceRecentData);
    }


    @GetMapping("/getTimeData")
    @ApiOperation(value = "获取每小时报道数据",notes = " \n author：lixiangxiang")
    public ResponseResult <Map<Integer, List<TimeRegisterDataDto>>>getTimeRegisterData(){
        Map<Integer, List<TimeRegisterDataDto>> timeRegisterNum = studentsService.getTimeRegisterNum();
        return ResponseResult.ok(timeRegisterNum);
    }

    @GetMapping("/getLatAndLng")
    @ApiOperation(value = "获取用户地址和经纬度",notes = " \n author：lixiangxiang")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "second",value = "间隔时间（秒）默认为5")
    })
    public ResponseResult<List<AddressDataDto>> getAddressAndLatAndLng(@RequestParam(name = "second",defaultValue = "5") int second){
        List<AddressDataDto> list = studentsService.getAddressAndLatAndLng(second);
        return ResponseResult.ok(list);
    }

    @GetMapping("/getAllLatAndLng")
    @ApiOperation(value = "获取所有数据的经纬度",notes = " \n author：Zhangpeitao")
    public ResponseResult<List<AddressDataDto>> getAllLatAndLng(){
        List<AddressDataDto> list = studentsService.getAllLatAndLng();
        return ResponseResult.ok(list);
    }


}
