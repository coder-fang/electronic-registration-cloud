package zone.jiefei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ZhangPeiTao
 * @since 2020-10-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentPraise对象", description="")
public class StudentPraise extends Model<StudentPraise> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id ")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "学生id")
    private Integer studentId;

    @ApiModelProperty(value = "微信的唯一标识")
    private String openid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
