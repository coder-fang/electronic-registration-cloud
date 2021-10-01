package zone.jiefei.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import zone.jiefei.dto.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "erc-students")
public interface IStudentsService {

    @GetMapping("/api/students/findRegisterData")
    List<RegistDataDto> findRegisterData(@RequestParam(name = "sex",required = false) Integer sex,
                                         @RequestParam(name = "type", required = false) Integer type,
                                         @RequestParam(name = "academy", required = false) String academy,
                                         @RequestParam(name = "batch", required = false) Integer batch,
                                         @RequestParam(name = "postgraduate",defaultValue = "0") Integer postgraduate);

    @GetMapping("/api/students/showStudentByCollegeAcademyClass")
    List<StudentProfileDto> showStudentByCollegeAcademyClass(
            @RequestParam(name = "type",required = false)Integer type,
            @RequestParam(name = "key",required = false)String key,
            @RequestParam(name = "status",required = false)Integer status,
            @RequestParam(name = "sex",required = false)Integer sex);

    @GetMapping("/api/students/findRecentData")
    List<RecentRegisterDataDto> findRecentData(@RequestParam(name = "num",defaultValue = "0")int num);

    @GetMapping("/api/students/getTimeRegisterNum")
    Map<Integer, List<TimeRegisterDataDto>> getTimeRegisterNum();

    @GetMapping("/api/students/getAddressAndLatAndLng")
    List<AddressDataDto> getAddressAndLatAndLng(@RequestParam(name = "second",defaultValue = "5") int second);

    @GetMapping("/api/students/getAllLatAndLng")
    List<AddressDataDto> getAllLatAndLng();
}
