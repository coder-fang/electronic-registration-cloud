package zone.jiefei.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value="OpenId对象", description="")
public class OpenId extends Model<OpenId> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String openid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
