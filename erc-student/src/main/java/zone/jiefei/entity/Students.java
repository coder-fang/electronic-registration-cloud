package zone.jiefei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ApiModel(value = "Students对象", description = "")
public class Students extends Model<Students> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "身份证号")
    private String cardId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "班级")
    private String classes;

    @ApiModelProperty(value = "学院（tt）")
    private String academy;

    @ApiModelProperty(value = "专业（tt）")
    private String major;

    @ApiModelProperty(value = "辅导员姓名")
    private String counselorName;

    @ApiModelProperty(value = "辅导员联系方式")
    private String counselorNum;

    @ApiModelProperty(value = "联系方式")
    private String phoneNum;

    @ApiModelProperty(value = "家庭联系方式")
    private String familyNum;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "微信号")
    private String wechatId;

    @ApiModelProperty(value = "QQ号")
    private String qqId;

    @ApiModelProperty(value = "宿舍id(tt)")
    private String dormitoryId;

    @ApiModelProperty(value = "是否报道 0:未报到/1:报道")
    private Integer status;

    @ApiModelProperty(value = "创建时间")
    @TableField(exist = false)
    private LocalDateTime createdAt;



    @ApiModelProperty(value = "政治面貌")
    @TableField("polApp")
    private String polApp;

    @ApiModelProperty(value = "确认注册0未确认,1确认")
    @TableField("conStatus")
    private Integer conStatus;

    @ApiModelProperty(value = "宿舍房间")
    @TableField("dorHouse")
    private String dorHouse;

    @TableField("dorOne")
    private String dorOne;

    @TableField("dorSe")
    private String dorSe;

    private String familyNumtwo;

    @ApiModelProperty(value = "0表示无宗教信仰，1表示有宗教信仰")
    private Integer religiousBelief;

    @ApiModelProperty(value = "推荐报道时间")
    private String reportTime;

    @ApiModelProperty(value = "批次")
    private Integer batch;

    @ApiModelProperty(value = "祝福数量")
    private Integer wishes;

    @ApiModelProperty(value = "学号")
    private String studentNumber;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
