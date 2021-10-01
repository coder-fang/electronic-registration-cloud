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
 * @since 2020-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Admin对象", description="")
public class Admin extends Model<Admin> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String account;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "账号类型")
    private String type;

    @ApiModelProperty(value = "账号权限")
    private Integer status;

    @ApiModelProperty(value = "token令牌")
    private String token;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
