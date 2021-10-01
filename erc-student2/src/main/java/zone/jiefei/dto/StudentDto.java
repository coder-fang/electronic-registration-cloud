package zone.jiefei.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @ClassName StudentAllMessage
 * @Description Students的dto，前端需要信息的提供
 * @Author zhanghaoqi
 * @Date 2020/10/05 20:55
 */
@Component
@Data
public class StudentDto {
    @ApiModelProperty(value = "姓名")
    private String name;


    @ApiModelProperty(value = "确认注册0未确认,1确认")
    private Integer conStatus;

    @ApiModelProperty(value = "学院（tt）")
    private String academy;

    @ApiModelProperty(value = "专业（tt）")
    private String major;

    @ApiModelProperty(value = "身份证号")
    private String cardId;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "政治面貌")
    private String polApp;

    @ApiModelProperty(value = "班级")
    private String classes;

    @ApiModelProperty(value = "宿舍id(tt)")
    private String dormitoryId;

    @ApiModelProperty(value = "辅导员姓名")
    private String counselorName;

    @ApiModelProperty(value = "辅导员联系方式")
    private String counselorNum;

    @ApiModelProperty(value = "微信号")
    private String wechatId;

    @ApiModelProperty(value = "QQ号")
    private String qqId;

    @ApiModelProperty(value = "联系方式")
    private String phoneNum;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "是否报道 0:未报到/1:报道")
    private Integer status;

    @ApiModelProperty(value = "0表示无宗教信仰，1表示有宗教信仰")
    private Integer religiousBelief;

    @ApiModelProperty(value = "推荐报道时间")
    private String reportTime;

}

