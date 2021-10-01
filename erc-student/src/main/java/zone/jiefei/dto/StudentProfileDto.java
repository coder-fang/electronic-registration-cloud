package zone.jiefei.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
/**
 * @author
 */
public class StudentProfileDto {

    //id
    @ApiModelProperty(value =  "id")
    private int id;
    //姓名
    @ApiModelProperty(value =  "姓名")
    private String name;
    //性别
    @ApiModelProperty(value =  "性别")
    private String sex;
    //班级
    @ApiModelProperty(value =  "班级")
    private String classes;

}
